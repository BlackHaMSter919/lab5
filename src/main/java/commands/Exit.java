package commands;

public class Exit implements Command{
    /**
     * Завершает выполнение программы.
     *
     * @return
     */
    @Override
    public void execute(String args) {
        {
            System.out.println("Завершаем программу...");
            System.exit(0);
        }


    }
}
