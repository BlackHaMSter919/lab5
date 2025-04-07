package commands;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Класс, который вывод справку по командам.
 */
@XmlRootElement(name = "Help")
public class Help implements Command{
    /**
     * Выводит справку по доступным командам.
     *
     * @return
     */
    @Override
    public void execute(String args){
        System.out.println("Доступные команды:");
        System.out.println("help : вывести справку");
        System.out.println("info : информация о коллекции");
        System.out.println("show : показать все элементы коллекции");
        System.out.println("add {element} : добавить новый элемент");
        System.out.println("remove_by_id {id} : удалить элемент по ID");
        System.out.println("clear : очистить коллекцию");
        System.out.println("save : сохранить коллекцию в файл");
        System.out.println("execute_script {file_name} : выполнить команды из файла");
        System.out.println("remove_first : удалить первый элемент из коллекции");
        System.out.println("remove_head : вывести и удалить первый элемент");
        System.out.println("add_if_min {element} : добавить элемент, если его значение минимально");
        System.out.println("max_by_albums_count : вывести элемент с максимальным albumsCount");
        System.out.println("count_less_than_best_album bestAlbum : вывести количество элементов, меньше заданного bestAlbum");
        System.out.println("print_field_ascending_number_of_participants : вывести количество участников в порядке возрастания");
        System.out.println("exit : завершить программу");

    }
}
