package sync.slamtalk.map.mapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import sync.slamtalk.map.dto.BasketballCourtFullResponseDTO;
import sync.slamtalk.map.dto.BasketballCourtReportResponseDTO;
import sync.slamtalk.map.dto.BasketballCourtReportSummaryDTO;
import sync.slamtalk.map.dto.BasketballCourtRequestDTO;
import sync.slamtalk.map.dto.BasketballCourtResponseDTO;
import sync.slamtalk.map.dto.BasketballCourtSummaryDto;
import sync.slamtalk.map.entity.AdminStatus;
import sync.slamtalk.map.entity.BasketballCourt;
import sync.slamtalk.map.entity.Fee;
import sync.slamtalk.map.entity.NightLighting;
import sync.slamtalk.map.entity.OpeningHours;
import sync.slamtalk.map.entity.ParkingAvailable;

@Component
public class BasketballCourtMapper {
    private static final String INITIAL_VALUE = "정보없음";

    // entity -> dto 변환
    public BasketballCourtSummaryDto toDto(BasketballCourt basketballCourt) {
        if (basketballCourt == null) {
            return null;
        }
        return new BasketballCourtSummaryDto(
                basketballCourt.getId(),
                basketballCourt.getCourtName(),
                basketballCourt.getAddress(),
                basketballCourt.getLatitude(),
                basketballCourt.getLongitude()
        );
    }

    public BasketballCourtReportSummaryDTO toStatusDto(BasketballCourt basketballCourt) {
        if (basketballCourt == null) {
            return null;
        }
        return new BasketballCourtReportSummaryDTO(
                basketballCourt.getId(),
                basketballCourt.getCourtName(),
                basketballCourt.getAddress(),
                basketballCourt.getLatitude(),
                basketballCourt.getLongitude(),
                basketballCourt.getAdminStatus().name()
        );
    }

    public BasketballCourtResponseDTO toFullDto(BasketballCourt basketballCourt) {
        if (basketballCourt == null) {
            return null;
        }

        return new BasketballCourtResponseDTO(
                basketballCourt.getId(),
                basketballCourt.getCourtName(),
                basketballCourt.getAddress(),
                basketballCourt.getLatitude(),
                basketballCourt.getLongitude(),
                basketballCourt.getCourtType(),
                basketballCourt.getIndoorOutdoor(),
                basketballCourt.getCourtSize(),
                basketballCourt.getHoopCount(),
                getPropertiesValue(basketballCourt.getNightLighting()),
                getPropertiesValue(basketballCourt.getOpeningHours()),
                getPropertiesValue(basketballCourt.getFee()),
                getPropertiesValue(basketballCourt.getParkingAvailable()),
                basketballCourt.getPhoneNum(),
                basketballCourt.getWebsite(),
                getConvenienceList(basketballCourt.getConvenience()),
                basketballCourt.getAdditionalInfo(),
                basketballCourt.getPhotoUrl(),
                basketballCourt.getInformerId()
        );
    }

    public BasketballCourtFullResponseDTO toFullChatDto(BasketballCourt basketballCourt) {
        if (basketballCourt == null) {
            return null;
        }

        return new BasketballCourtFullResponseDTO(
                basketballCourt.getId(),
                basketballCourt.getCourtName(),
                basketballCourt.getAddress(),
                basketballCourt.getLatitude(),
                basketballCourt.getLongitude(),
                basketballCourt.getCourtType(),
                basketballCourt.getIndoorOutdoor(),
                basketballCourt.getCourtSize(),
                basketballCourt.getHoopCount(),
                getPropertiesValue(basketballCourt.getNightLighting()),
                getPropertiesValue(basketballCourt.getOpeningHours()),
                getPropertiesValue(basketballCourt.getFee()),
                getPropertiesValue(basketballCourt.getParkingAvailable()),
                basketballCourt.getPhoneNum(),
                basketballCourt.getWebsite(),
                getConvenienceList(basketballCourt.getConvenience()),
                basketballCourt.getAdditionalInfo(),
                basketballCourt.getPhotoUrl(),
                basketballCourt.getInformerId(),
                basketballCourt.getChatroom().getId()
        );
    }



    public BasketballCourtReportResponseDTO toFullStatusDto(BasketballCourt basketballCourt) {
        if (basketballCourt == null) {
            return null;
        }

        return new BasketballCourtReportResponseDTO(
                basketballCourt.getId(),
                basketballCourt.getCourtName(),
                basketballCourt.getAddress(),
                basketballCourt.getLatitude(),
                basketballCourt.getLongitude(),
                basketballCourt.getCourtType(),
                basketballCourt.getIndoorOutdoor(),
                basketballCourt.getCourtSize(),
                basketballCourt.getHoopCount(),
                getPropertiesValue(basketballCourt.getNightLighting()),
                getPropertiesValue(basketballCourt.getOpeningHours()),
                getPropertiesValue(basketballCourt.getFee()),
                getPropertiesValue(basketballCourt.getParkingAvailable()),
                basketballCourt.getPhoneNum(),
                basketballCourt.getWebsite(),
                getConvenienceList(basketballCourt.getConvenience()),
                basketballCourt.getAdditionalInfo(),
                basketballCourt.getPhotoUrl(),
                basketballCourt.getInformerId(),
                basketballCourt.getAdminStatus().name()
        );
    }

    public BasketballCourt toEntity(BasketballCourtRequestDTO dto, String photoUrl , Long userId) {
        if (dto == null) {
            return null;
        }

        NightLighting nightLighting = NightLighting.fromString(dto.getNightLighting());
        OpeningHours openingHours = OpeningHours.fromString(dto.getOpeningHours());
        Fee fee = Fee.fromString(dto.getFee());
        ParkingAvailable parkingAvailable = ParkingAvailable.fromString(dto.getParkingAvailable());

        return BasketballCourt.builder()
                .courtName((dto.getCourtName()))
                .address((dto.getAddress()))
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .courtType(dto.getCourtType())
                .indoorOutdoor(dto.getIndoorOutdoor())
                .courtSize(dto.getCourtSize())
                .hoopCount(dto.getHoopCount())
                .nightLighting(nightLighting)
                .openingHours(openingHours)
                .fee(fee)
                .parkingAvailable(parkingAvailable)
                .phoneNum((dto.getPhoneNum()))
                .website((dto.getWebsite()))
                .convenience(dto.getConvenience())
                .additionalInfo(dto.getAdditionalInfo())
                .photoUrl(photoUrl)
                .adminStatus(AdminStatus.STAND) // 대기 상태
                .informerId(userId)
                .build();

    }

    // 편의 시설 초깃값 설정
    private List<String> getConvenienceList(String convenience) {
        return convenience != null ? Arrays.asList(convenience.split(",")) : Collections.emptyList();
    }

    // 빈 값일 때, 초깃값 설정
    private String getPropertiesValue(Enum<?> propertiesValue) {
        return propertiesValue != null ? propertiesValue.name() : INITIAL_VALUE;
    }
}
