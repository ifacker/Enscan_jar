package plugins.Enscan.util;

public class Cmd_test {
    public static void main(String[] args) {
        String os = System.getProperty("os.name");
        System.out.println(os);

        String arch = System.getProperty("os.arch");
        System.out.printf(arch);

        Cmd.run("exec/enscan_m -n 小米 -field icp");
    }
}
