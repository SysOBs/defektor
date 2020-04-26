package pt.uc.sob.defektor;

/**
 * Defektor main class.
 */
public class Defektor {

    /**
     * Main class for defektor.
     *
     * @param args - None.
     */
    public static void main(String[] args) {
        System.out.println("🐳 Defektor - Write once, run away");

        PluginController pluginController = new PluginController();

        pluginController.load();

        System.out.println("🔌 Plugins list: " + pluginController.getPluginsList().toString());

        try {
            System.out.println("😎 Doing some stuff");
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pluginController.load();

        System.out.println("🔌 Plugins list: " + pluginController.getPluginsList().toString());

        pluginController.start(null);

        pluginController.stop(null);
    }
}
