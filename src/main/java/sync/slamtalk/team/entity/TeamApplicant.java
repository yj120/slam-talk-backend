package sync.slamtalk.team.entity;

import jakarta.persistence.*;
import lombok.*;
import sync.slamtalk.common.BaseEntity;
import sync.slamtalk.common.BaseException;
import sync.slamtalk.mate.entity.ApplyStatusType;
import sync.slamtalk.team.dto.ToApplicantDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sync.slamtalk.team.error.TeamErrorResponseCode.TEAM_POST_NOT_FOUND;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TeamApplicant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long teamApplicantTableId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_matching_id")
    private TeamMatching teamMatching;
    @Column(nullable = false)
    private long applicantId;
    @Column(nullable = false)
    private String applicantNickname;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplyStatusType applyStatus; // WAITING, ACCEPTED, REJECTED, CANCELED
    private long chatroomId;

    public void connectTeamMatching(TeamMatching teamMatching) {
        this.teamMatching = teamMatching;
        teamMatching.getTeamApplicants().add(this);
    }



    public ToApplicantDto makeDto(){
        ToApplicantDto dto = new ToApplicantDto();
        dto.setApplicantId(this.applicantId);
        dto.setApplicantNickname(this.applicantNickname);
        dto.setApplyStatusType(this.applyStatus);
        dto.setChatroomId(this.chatroomId);
        dto.setTeamApplicantTableId(this.teamApplicantTableId);
        dto.setTeamMatchingId(this.teamMatching.getTeamMatchingId());
        return dto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TeamApplicant that = (TeamApplicant) o;
        return teamApplicantTableId == that.teamApplicantTableId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamApplicantTableId);
    }

    @Override
    public String toString() {
        return "TeamApplicant{" +
                "teamApplicantTableId=" + teamApplicantTableId +
                ", teamMatchingId=" + teamMatching.getTeamMatchingId() +
                ", applicantId=" + applicantId +
                ", applicantNickname='" + applicantNickname + '\'' +
                ", isChatroomCreated=" + applyStatus +
                ", chatroomId=" + chatroomId +
                '}';
    }

}