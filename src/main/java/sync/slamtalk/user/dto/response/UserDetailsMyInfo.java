package sync.slamtalk.user.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import sync.slamtalk.user.entity.SocialType;
import sync.slamtalk.user.entity.User;
import sync.slamtalk.user.entity.UserRole;
import sync.slamtalk.user.utils.UserLevelScore;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class UserDetailsMyInfo {
    /* 개인 정보 관련 */
    private String email;
    private SocialType socialType;
    private UserRole role;

    /* 공개되어도 상관없는 부분 */
    private Long id;
    private String nickname;
    private String imageUrl;

    /* 마이페이지 기능 */
    private String selfIntroduction;

    /* 정보 수집 부분 */
    private String basketballSkillLevel;
    private String basketballPosition;
    private long level;
    private long levelScore;
    private long mateCompleteParticipationCount;
    private long teamMatchingCompleteParticipationCount;

    /**
     * 나의 프로필 조회 시 필요한 정보를 반환하는 생성자
     *
     * @param user                           db에서 조회한 user 객체
     * @param mateCompleteParticipationCount 메이트 참여완료 횟수
     * @return UserDetailsInfoResponseDto 개인정보 포함된 정보
     */
    public static UserDetailsMyInfo generateMyProfile(
            User user,
            long levelScore,
            long mateCompleteParticipationCount,
            long teamCompleteParticipationCount
    ) {
        String basketballSkillLevel = user.getBasketballSkillLevel() != null ? user.getBasketballSkillLevel().getLevel() : null;
        String basketballPosition = user.getBasketballPosition() != null ? user.getBasketballPosition().getPosition() : null;
        long level = levelScore / UserLevelScore.LEVEL_THRESHOLD;

        return UserDetailsMyInfo.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .imageUrl(user.getImageUrl())
                .selfIntroduction(user.getSelfIntroduction())
                .basketballSkillLevel(basketballSkillLevel)
                .basketballPosition(basketballPosition)
                .level(level)
                .levelScore(levelScore)
                .mateCompleteParticipationCount(mateCompleteParticipationCount)
                .teamMatchingCompleteParticipationCount(teamCompleteParticipationCount)
                .email(user.getEmail())
                .socialType(user.getSocialType())
                .role(user.getRole())
                .build();
    }
}
