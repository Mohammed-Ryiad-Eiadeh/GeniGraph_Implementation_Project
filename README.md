# GeniGraph_Project

# Paper Tiltle: "GeniGraph: Genetic-based Novel Security Resource Allocation Methods for Interdependent Systems Modeled by Attack Graphs".

# abstract
We design a resource allocation framework for securing interdependent systems managed by multiple defenders. Our framework models these multi-defender interdependent systems with the notion of attack graphs. We propose three defense scenarios that are derived from the top attack paths that defenders predict, based on their system knowledge, which attackers may consider to launch their attacks. 

Furthermore, we propose a defense method with low sensitivity to the number of concurrent attacks, based on a graph-theoretical notion known as the Markov random field. We elucidate the advantages gained from our decision-making framework through its application to ten attack graphs (that includes multiple real-world interdependent systems). In particular, we quantify the level of security improvement under our defense methods compared to three well-known resource allocation algorithms. 

We demonstrate that our proposed framework leads to better resource allocations compared to these algorithms in most scenarios. We also conduct various comparisons under different parameter configurations (e.g., number of attacks, and defenders' security budgets) to demonstrate the superiority of our approach’s outcomes. We show that our framework enhances security decision-making under various circumstances.

# Framework
![GeniGraph](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/f388ec44-edde-4465-a361-8a4fdc303df5) In our work, we start with cyber attack graph, which serves as the input for our genetic algorithm. This algorithm is designed to produce top-K potential attack paths through a series of steps including path encoding, reproduction, crossover, and mutation. Concurrently, we select a suitable fitness function and calculate the fitness and cost relative difference. The outputs from both these processes lead to the top-K paths. These paths are then utilized in various resource allocation defense scenarios, including the best attack path, attack paths equally-based, and weight-based, and Markov Random Field. Based on the evaluation of these scenarios, we make our investment decisions. Essentially, we’re optimizing investment decisions using a genetic algorithm and an attack graph, where the attack graph represents a complex system or network that the algorithm navigates to find optimal paths for investment from the prospective of defender anticipating the attacker role. The defense scenarios represent different strategies for resource allocation in response to potential risks or ‘attacks’ on the investment. Our final decision is made based on the evaluation of these scenarios.

# Our Contribution
1) We propose a resource allocation method for defenders of interdependent systems, where the subnetworks they defend are mutually interdependent. We demonstrate the impact of GeniGraph on decision-making related to system security, and we quantify the percentage of improvement attributable to our security resource allocation method.
2) We propose two versions of GeniGraph to improve resource allocation decisions under three defense scenarios (that are based on top attack paths) against different attack models that affect the security of interdependent systems
