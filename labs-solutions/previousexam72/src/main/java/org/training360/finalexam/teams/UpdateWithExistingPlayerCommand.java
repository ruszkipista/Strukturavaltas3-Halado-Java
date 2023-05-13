package org.training360.finalexam.teams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateWithExistingPlayerCommand {

    private long playerId;
}
