package configuration.common;

public class TestDemo {
    public static void main(String[] args) {
        String currentDirectory=System.getProperty("user.dir");
        String path=System.getProperty("user.dir")+"/browserDriver/chromedriver";
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/browserDriver/chromedriver");//sub folder for mac//mac no .exe
        System.out.println(path);
        System.out.println(currentDirectory);
    }
}
