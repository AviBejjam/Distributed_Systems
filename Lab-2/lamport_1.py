# Function to find the timestamp of events
def lamportLogicalClock(e1, e2, e3, e4, m) : 
    p1 = [0]*e1
    p2 = [0]*e2
    p3 = [0]*e3
    p4 = [0]*e4

    p1 = [i + 1 for i in range(e1)]
    p2 = [i + 1 for i in range(e2)]
    p3 = [i + 1 for i in range(e3)]
    p4 = [i + 1 for i in range(e4)]

    # Process messages based on the matrix
    for i in range(4):  # for each process
        for j in range(4):  # for each other process
            if m[i][j] == 1:  # message is sent from process i to process j
                if i == 0 and j == 1:  # from P1 to P2
                    print(f"Message sent from P1(e{e1}) to P2(e{e2})")
                    print(f"Before update: P1 = {p1}, P2 = {p2}")
                    p2[0] = max(p2[0], p1[1] + 1)
                    for k in range(1, e2):
                        p2[k] = p2[k - 1] + 1
                    print(f"After update: P2 = {p2}")
                elif i == 1 and j == 2:  # from P2 to P3
                    print(f"Message sent from P2(e{e2}) to P3(e{e3})")
                    print(f"Before update: P2 = {p2}, P3 = {p3}")
                    p3[0] = max(p3[0], p2[1] + 1)
                    for k in range(1, e3):
                        p3[k] = p3[k - 1] + 1
                    print(f"After update: P3 = {p3}")
                elif i == 2 and j == 3:  # from P3 to P4
                    print(f"Message sent from P3(e{e3}) to P4(e{e4})")
                    print(f"Before update: P3 = {p3}, P4 = {p4}")
                    p4[0] = max(p4[0], p3[2] + 1)
                    for k in range(1, e4):
                        p4[k] = p4[k - 1] + 1
                    print(f"After update: P4 = {p4}")
                elif i == 3 and j == 2:  # from P4 to P3
                    print(f"Message sent from P4(e{e4}) to P3(e{e3})")
                    print(f"Before update: P4 = {p4}, P3 = {p3}")
                    p3[2] = max(p3[2], p4[2] + 1)
                    for k in range(3, e3):
                        p3[k] = p3[k - 1] + 1
                    print(f"After update: P3 = {p3}")

    # Function Call
    # display(e1, e2, e3, e4, p1, p2, p3, p4)

# Driver Code
if __name__ == "__main__" : 
    e1 = 2
    e2 = 2
    e3 = 3
    e4 = 3

    m = [[0]*4 for _ in range(4)]

    # Initialize message matrix
    m[0][1] = 1  # e12 -> e22
    m[1][2] = 1  # e21 -> e31
    m[2][3] = 1  # e31 -> e42
    m[3][2] = 1  # e42 -> e33

    # Function Call
    lamportLogicalClock(e1, e2, e3, e4, m)
