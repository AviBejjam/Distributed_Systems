class Process:
    def __init__(self, process_id, total_processes):
        self.process_id = process_id
        self.clock = 0
        self.vector_clock = [0] * total_processes  # Vector clock initialized to zeros

    def increment_clock(self):
        self.clock += 1

    def internal_event(self):
        self.increment_clock()
        self.vector_clock[self.process_id] += 1
        print(f"Process {self.process_id} performs an internal event. Lamport clock: {self.clock}, Vector clock: {self.vector_clock}")

    def send_message(self, receiver):
        self.increment_clock()
        self.vector_clock[self.process_id] += 1
        print(f"Process {self.process_id} sends a message to Process {receiver.process_id} with Lamport clock: {self.clock}, Vector clock: {self.vector_clock}")
        receiver.receive_message(self.clock, self.vector_clock)

    def receive_message(self, sender_clock, sender_vector_clock):
        self.clock = max(self.clock, sender_clock) + 1
        self.vector_clock = [max(self.vector_clock[i], sender_vector_clock[i]) for i in range(len(self.vector_clock))]
        self.vector_clock[self.process_id] += 1
        print(f"Process {self.process_id} receives a message. Updated Lamport clock: {self.clock}, Vector clock: {self.vector_clock}")

    def get_lamport_clock(self):
        return self.clock

    def get_vector_clock(self):
        return self.vector_clock

def simulate_distributed_system():
    # Number of processes in the distributed system
    total_processes = 4
    
    # Initialize processes
    processes = [Process(process_id=i, total_processes=total_processes) for i in range(total_processes)]

    # Simulate events and message passing
    processes[0].internal_event()  # P1 internal event
    processes[1].internal_event()  # P2 internal event
    processes[0].send_message(processes[1])  # P1 sends a message to P2
    processes[2].internal_event()  # P3 internal event
    processes[1].send_message(processes[2])  # P2 sends a message to P3
    processes[3].internal_event()  # P4 internal event
    processes[2].send_message(processes[3])  # P3 sends a message to P4
    processes[1].send_message(processes[3])  # P2 sends a message to P4
    processes[3].internal_event()  # P4 internal event
    processes[3].send_message(processes[0])  # P4 sends a message to P1

    # Display final clock states
    print("\nFinal states of processes:")
    for process in processes:
        print(f"Process {process.process_id}: Lamport clock = {process.get_lamport_clock()}, Vector clock = {process.get_vector_clock()}")

# Run the simulation
simulate_distributed_system()
