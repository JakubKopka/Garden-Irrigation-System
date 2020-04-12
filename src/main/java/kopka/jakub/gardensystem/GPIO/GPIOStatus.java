package kopka.jakub.gardensystem.GPIO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GPIOStatus {
    private String gpioNumber;
    private String state;

}
