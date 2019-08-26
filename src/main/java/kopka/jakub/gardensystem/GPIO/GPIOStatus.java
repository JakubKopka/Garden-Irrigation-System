package kopka.jakub.gardensystem.GPIO;

public class GPIOStatus {
    private String gpioNumber;
    private String state;


    public GPIOStatus(String gpioNumber, String status) {
        this.gpioNumber = gpioNumber;
        this.state = status;
    }

    public String getGpioNumber() {
        return gpioNumber;
    }

    public void setGpioNumber(String gpioNumber) {
        this.gpioNumber = gpioNumber;
    }

    public String getStatus() {
        return state;
    }

    public void setStatus(String state) {
        this.state = state;
    }
}
