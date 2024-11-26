package com.gaseomwohae.gaseomwohae.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gaseomwohae.gaseomwohae.dto.travel.TourismStatsDto;
import com.gaseomwohae.gaseomwohae.dto.travel.WeatherResponseDto;

@Service
public class ApiService {

    private static final Logger logger = LoggerFactory.getLogger(ApiService.class);

    @Value("${api.serviceKey}")
    private String serviceKey;

    private final WebClient.Builder webClientBuilder;

    public ApiService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    // API 호출과 함께 변환된 X, Y 좌표를 사용하는 메서드
    public List<WeatherResponseDto> getWeatherInfo(double lat, double lng) {
        // 위도 경도를 X, Y 좌표로 변환
        LatXLngY convertedCoordinates = convertGRID_GPS(0, lat, lng);

        // 변환된 X, Y 좌표를 사용하여 API 요청 URL을 작성
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";

        List<String> baseTimes = List.of("0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300");
        String currentBaseTime = getCurrentBaseTime(baseTimes);

        String baseDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int nx = (int) Math.round(convertedCoordinates.x);
        int ny = (int) Math.round(convertedCoordinates.y);

        String response = WebClient.create(url)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", 1000)
                        .queryParam("pageNo", 1)
                        .queryParam("dataType", "JSON")
                        .queryParam("base_date", baseDate)
                        .queryParam("base_time", currentBaseTime)
                        .queryParam("nx", nx)
                        .queryParam("ny", ny)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return processWeatherData(response);
    }

    public List<TourismStatsDto> getTourismStats(String city, String district) {
        String url = "http://openapi.tour.go.kr/openapi/service/TourismResourceStatsService/getPchrgTrrsrtVisitorList";
        LocalDate currentDate = LocalDate.now();
        List<TourismStatsDto> tourismStatsList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            String yearMonth = currentDate.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyyMM"));
            
            String response = WebClient.create(url)
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("serviceKey", "7RWj0xvK9L9UiCrhvDYxqjmfXYXmu%2FDk2BqIIKjHfgu1qo88v0e%2FOoI7FCjDP3i57S35lqU75vcGDvm72O5wWA%3D%3D")
                            .queryParam("YM", yearMonth)
                            .queryParam("SIDO", city)
                            .queryParam("GUNGU", district)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            tourismStatsList.addAll(processTourismDataFromXml(response));
        }

        return tourismStatsList;
    }

    private String getCurrentBaseTime(List<String> baseTimes) {
        LocalTime now = LocalTime.now();
        LocalTime lastBaseTime = LocalTime.parse(baseTimes.get(0), DateTimeFormatter.ofPattern("HHmm"));

        for (String baseTime : baseTimes) {
            LocalTime time = LocalTime.parse(baseTime, DateTimeFormatter.ofPattern("HHmm"));
            if (now.isBefore(time)) {
                return lastBaseTime.format(DateTimeFormatter.ofPattern("HHmm"));
            }
            lastBaseTime = time;
        }
        return lastBaseTime.format(DateTimeFormatter.ofPattern("HHmm")); // Return the last base time if none match
    }
    
    // LCC DFS 좌표변환 메서드
    // 위도(latitude)는 x, 경도(longitude)는 y로 매핑됨
    private LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y) {
        double RE = 6371.00877; // 지구 반경(km)
        double GRID = 5.0; // 격자 간격(km)
        double SLAT1 = 30.0; // 투영 위도1(degree)
        double SLAT2 = 60.0; // 투영 위도2(degree)
        double OLON = 126.0; // 기준점 경도(degree)
        double OLAT = 38.0; // 기준점 위도(degree)
        double XO = 43; // 기준점 X좌표(GRID)
        double YO = 136; // 기준점 Y좌표(GRID)

        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        LatXLngY rs = new LatXLngY();

        if (mode == 0) { // TO_GRID
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        } else { // TO_GPS
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            } else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                } else {
                    theta = Math.atan2(xn, yn);
                }
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }

    // 좌표 변환 결과를 담을 LatXLngY 클래스
    public static class LatXLngY {
        public double lat;
        public double lng;
        public double x;
        public double y;
    }

    private List<WeatherResponseDto> processWeatherData(String response) {
        List<WeatherResponseDto> weatherDataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Integer> minTemps = new HashMap<>();
        Map<String, Integer> maxTemps = new HashMap<>();
        Map<String, Integer> maxSky = new HashMap<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            for (JsonNode itemNode : itemsNode) {
                String fcstDate = itemNode.path("fcstDate").asText();
                String category = itemNode.path("category").asText();
                int fcstValue = itemNode.path("fcstValue").asInt();

                if (isWithinNextThreeDays(fcstDate)) {
                    if (category.equals("TMP")) {
                        minTemps.merge(fcstDate, fcstValue, Integer::min);
                        maxTemps.merge(fcstDate, fcstValue, Integer::max);
                    } else if (category.equals("SKY")) {
                        int skyValue = fcstValue;
                            maxSky.merge(fcstDate, skyValue, Integer::max);
                    } else if (category.equals("PTY")){
                        int skyValue = fcstValue;
                        if(skyValue == 1){
                            maxSky.merge(fcstDate, 5, Integer::max);
                        }
                        else if(skyValue == 2){
                            maxSky.merge(fcstDate, 6, Integer::max);
                        }
                        else if(skyValue == 3){
                            maxSky.merge(fcstDate, 7, Integer::max);
                        }
                    }
                }
            }

            for (String date : minTemps.keySet()) {
                weatherDataList.add(WeatherResponseDto.builder()
                        .date(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd")))
                        .minTemp(minTemps.get(date))
                        .maxTemp(maxTemps.get(date))
                        .sky(maxSky.getOrDefault(date, 0))
                        .build());
            }

        } catch (Exception e) {
            return null;
        }

        return weatherDataList;
    }

    private boolean isWithinNextThreeDays(String fcstDate) {
        LocalDate date = LocalDate.parse(fcstDate, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate today = LocalDate.now();
        return !date.isBefore(today) && !date.isAfter(today.plusDays(2));
    }


    
    private List<TourismStatsDto> processTourismDataFromXml(String response) {
        List<TourismStatsDto> tourismDataList = new ArrayList<>();
        XmlMapper xmlMapper = new XmlMapper();

        try {
            JsonNode rootNode = xmlMapper.readTree(response.getBytes());
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

            for (JsonNode itemNode : itemsNode) {
                String date = itemNode.path("ym").asText();
                int visitorCount = 0;
                if(itemNode.path("csForCnt").asText() != null){
                    visitorCount = itemNode.path("csForCnt").asInt();
                }
                if(itemNode.path("csNatCnt").asText() != null){
                    visitorCount = itemNode.path("csNatCnt").asInt();
                }


                tourismDataList.add(TourismStatsDto.builder()
                        .date(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM")))
                        .visitorCount(visitorCount)
                        .build());
            }
        } catch (Exception e) {
            return null;
        }

        return tourismDataList;
    }
}
