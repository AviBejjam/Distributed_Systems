import java.util.concurrent.BlockingQueue;

public class Worker implements Runnable {
    private int workerId;
    private BlockingQueue<String> taskQueue;

    public Worker(int id, BlockingQueue<String> queue) {
        this.workerId = id;
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Take the next task from the queue (blocks if no tasks are available)
                String task = taskQueue.take();
                System.out.println("\nWorker " + workerId + " processing task: " + task);
                System.out.println();
                // Simulate task processing time
                Thread.sleep(2000);  // Simulates the time to process the task
                System.out.println("Worker " + workerId + " completed task: " + task);
                System.out.println();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
