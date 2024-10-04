import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        LoadBalancer loadBalancer = LoadBalancer.getInstance(); // Get the singleton instance of LoadBalancer

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter task to send to LoadBalancer: ");
            String task = scanner.nextLine();

            loadBalancer.addTask(task);  // Send task to the load balancer
        }
    }
}
