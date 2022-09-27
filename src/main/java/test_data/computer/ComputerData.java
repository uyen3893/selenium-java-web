package test_data.computer;

public class ComputerData {

    private String processorType;
    private String ram;
    private String hdd;
    private String software;
    private String os;

    public ComputerData(String processorType, String ram, String hdd, String software, String os) {
        this.processorType = processorType;
        this.ram = ram;
        this.hdd = hdd;
        this.software = software;
        this.os = os;
    }

    public String getProcessorType() {
        return processorType;
    }

    public String getRam() {
        return ram;
    }

    public String getHdd() {
        return hdd;
    }

    public String getSoftware() {
        return software;
    }

    public String getOs() {
        return os;
    }

    @Override
    public String toString() {
        return "ComputerData{" +
                "processorType='" + processorType + '\'' +
                ", ram='" + ram + '\'' +
                ", hdd='" + hdd + '\'' +
                ", software='" + software + '\'' +
                ", os='" + os + '\'' +
                '}';
    }
}
