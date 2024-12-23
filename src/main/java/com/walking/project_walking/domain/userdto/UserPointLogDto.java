package com.walking.project_walking.domain.userdto;

import com.walking.project_walking.domain.PointLog;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPointLogDto {

  private Integer amount;
  private String description;
  private LocalDateTime time;

  public UserPointLogDto(PointLog pointLog) {
    amount = pointLog.getAmount();
    description = pointLog.getDescription();
    time = pointLog.getTime();
  }
}
