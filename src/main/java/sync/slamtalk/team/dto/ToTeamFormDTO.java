package sync.slamtalk.team.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import sync.slamtalk.mate.entity.RecruitedSkillLevelType;
import sync.slamtalk.mate.entity.RecruitmentStatusType;
import sync.slamtalk.team.entity.TeamApplicant;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToTeamFormDTO {

    private long teamMatchingId;

    @NotBlank(message = "팀명을 입력해주세요.")
    private String teamName;

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NonNull
    private long writerId;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "상세 위치를 입력해주세요.")
    private String locationDetail;

    @NonNull
    private int numberOfMembers;

    @NonNull
    private RecruitedSkillLevelType skillLevel;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startScheduledTime;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endScheduledTime;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @NonNull
    @Enumerated(EnumType.STRING)
    private RecruitmentStatusType recruitmentStatusType;

    @NonNull
    private List<ToApplicantDto> teamApplicantsDto;
}