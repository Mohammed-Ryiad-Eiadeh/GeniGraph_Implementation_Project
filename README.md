# GeniGraph_Project.

# Paper Tiltle: "GeniGraph: Genetic-based Novel Security Resource Allocation Methods for Interdependent Systems Modeled by Attack Graphs".

# abstract.
We design a resource allocation framework for securing interdependent systems managed by multiple defenders. Our framework models these multi-defender interdependent systems with the notion of attack graphs. We propose three defense scenarios that are derived from the top attack paths that defenders predict, based on their system knowledge, which attackers may consider to launch their attacks. 

Furthermore, we propose a defense method with low sensitivity to the number of concurrent attacks, based on a graph-theoretical notion known as the Markov random field. We elucidate the advantages gained from our decision-making framework through its application to ten attack graphs (that includes multiple real-world interdependent systems). In particular, we quantify the level of security improvement under our defense methods compared to three well-known resource allocation algorithms. 

We demonstrate that our proposed framework leads to better resource allocations compared to these algorithms in most scenarios. We also conduct various comparisons under different parameter configurations (e.g., number of attacks, and defenders' security budgets) to demonstrate the superiority of our approach’s outcomes. We show that our framework enhances security decision-making under various circumstances.

# Framework.
![GeniGraph](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/f388ec44-edde-4465-a361-8a4fdc303df5) 

In our work, we start with cyber attack graph, which serves as the input for our genetic algorithm. This algorithm is designed to produce top-K potential attack paths through a series of steps including path encoding, reproduction, crossover, and mutation. Concurrently, we select a suitable fitness function and calculate the fitness and cost relative difference. The outputs from both these processes lead to the top-K paths. These paths are then utilized in various resource allocation defense scenarios, including the best attack path, attack paths equally-based, and weight-based, and Markov Random Field. Based on the evaluation of these scenarios, we make our investment decisions. Essentially, we’re optimizing investment decisions using a genetic algorithm and an attack graph, where the attack graph represents a complex system or network that the algorithm navigates to find optimal paths for investment from the prospective of defender anticipating the attacker role. The defense scenarios represent different strategies for resource allocation in response to potential risks or ‘attacks’ on the investment. Our final decision is made based on the evaluation of these scenarios.

# Fitness Function
1) $F_1(P)= \max_{P \in P_m} \big(\exp\big(-\sum_{(v_i,v_j)\in P} {x_{i,j}}\big)\big).$
2) $F_2(P) = \max_{P \in P_m} \big(\exp\big(-\sum_{(v_i,v_j)\in P} {x_{i,j}}\big) + Wf\sum_{v_m\in P} L_m\big).$
   
   $P$ is the given attack path.

   $P_m$ is a set of attack paths.

   $v_i,v_j$ are the nodes in $P$.

   $L_m$ is the loss corresponding to node $v_m$

   $Wf$ is the weight factor lies in [0,1]
   
First function cares only about the initial investments of the path $P$. On the other hand, second function accounts for the total asset loss that the system will loos if the attack is occured successfully.

# Cyber Attack Graph Example with Assets and Entry Nodes With Three Defenders to Allocate Resourcess.
![General_interdependent_network_legend](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/e0eda941-decf-4d34-b3aa-cd9073e06dc7)

# Neumarical Cyber Attack Graph Example
![Screenshot (418)](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/e19e1bca-49a2-4c8e-9641-ef50fb71bb2c)

# Time Complexity of GeniGraph with the Respect to Number of Nodes and Edges Respictively.
![NodeCurve](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/c8578c86-c4c5-44cb-9bd8-8544b25f3ebd)

![EdgeCurve](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/02044938-0c1a-4ac5-8328-305c67493934)

As the number of nodes or edges increases, we notice that the actual run-time remains relatively quick. It doesn’t display a pattern of exponential growth. The lines representing the two fitness functions, rational-𝐹1 and rational-𝐹2, in the figures do not exhibit the sharp curve usually associated with exponential growth. In fact, rational-𝐹1 shows superior performance with a slightly curved line, while rational-𝐹2 is nearly linear. This suggests that the run-time of GeniGraph grows at a rate that is not exponentia.


# Our Contribution.
1) We propose a resource allocation method for interdependent systems, demonstrating GeniGraph’s impact on system security and quantifying the improvement it brings.
2) We offer two versions of GeniGraph to enhance resource allocation decisions under three defense scenarios against various attack models.
3) We use a genetic algorithm to generate potential paths from the attacker’s entry node to the target, proposing two fitness functions. The first considers edge weights, while the second also accounts for estimated financial loss.
4) We evaluate our defense methods on ten attack graphs and compare the performance of three popular resource allocation methods.
5) We implement our framework in Java, using object-oriented programming and reliable libraries for graph-theoretic algorithms.

# Differencess Among our Model and Existed Ones. 
| System | Multiple Defenders | Interdependent Subnetworks | Analytical Framework | Behavioral Biases | Various Attack Types | Multiple Rounds | Top Attack Paths | Graph Type |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| RAID08, MILCOM06 | ❌ | ✔️ | ❌ | ❌ | ❌ | ❌ | ❌ | Directed |
| S&P02, CCS12 | ❌ | ❌ | ✔️ | ❌ | ❌ | ❌ | ❌ | Directed |
| S&P09, EC18, ACSAC12 | ❌ | ❌ | ❌ | ✔️ | ❌ | ❌ | ❌ | Directed |
| ICC17 | ❌ | ✔️ | ✔️ | ✔️ | ❌ | ❌ | ❌ | Directed |
| TCNS20, TCNS18 | ✔️ | ✔️ | ✔️ | ✔️ | ❌ | ❌ | ❌ | Directed |
| Behavioral Defender | ✔️ | ✔️ | ✔️ | ✔️ | ✔️ | ✔️ | ❌ | Directed |
| GeniGraph | ✔️ | ✔️ | ✔️ | ❌ | ✔️ | ✔️ | ✔️ | Bidirectional |

# Datasets We Used In Our Work
| System | # Nodes | # Edges | # Critical Assets | $v_s$ / $v_m$ | Graph Type | run-time $F_1$ | run-time $F_2$ |
| --- | --- | --- | --- | --- | --- | --- | --- |
| SCADA | 13 | 20 | 6 | 1 / 13 | Bidirectional | 4921 | 5858 |
| DER.1 | 22 | 32 | 6 | 1 / 22 | Bidirectional | 8780 | 16389 |
| E-Commerce | 20 | 32 | 4 | 1 / 20 | Bidirectional | 10256 | 17902 |
| VOIP | 22 | 35 | 4 | 1 / 22 | Bidirectional | 11810 | 22046 |
| HG1 | 7 | 10 | 2 | 1 / 7 | Bidirectional | 3347 | 4749 |
| HG2 | 15 | 42 | 5 | 1 / 12 | Bidirectional | 5436 | 6560 |
| ABSNP | 17 | 122 | 6 | 1 / 17 | Bidirectional | 8107 | 6811 |
| ASFS3 | 27 | 163 | 9 | 1 / 27 | Bidirectional | 11998 | 27400 |
| ASS2009 | 31 | 211 | 9 | 1 / 31 | Bidirectional | 17404 | 32746 |
| AWS03 | 42 | 152 | 15 | 1 / 42 | Bidirectional | 12218 | 39465 |

# Parameter Configuration of Our Experiments
The following parameters were used: maximum iteration ($M=2000$), population size (set of potential attack paths) ($N=2000$), mutation probability ($m_p=0.2$), mutation rate ($m_r=0.2$), and weight factor ($Wf=0.001$). The available security budgets for the defenders were $S_1=1$, $S_2=0.75$, and $S_3=0.5$. We underscore that our proposed defense strategies are effective regardless of the security budget, as demonstrated in our evaluation experiments. For the behavioral defender, the behavioral level ($a$) was set to 0.5. All experiments were performed using Java language (JDK 17) on a machine equipped with an Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz (12 CPUs), and 16384MB RAM.

# Comparison of GeniGraph and baseline systems on all datasets.
The row "Measurements" show the relative difference $RD$ of the fitness score corresponding to fitness function $F_1$, and the relative difference of the expected cost $CR$ for all defense scenarios. The larger $RD$ and $CR$, the better the defense method with significance level equals 0.05 for the Friedman test.

1) Top-1 attack path.
   
| System | GeniGraph | Min-cut | Equally Distributed | Behavioral Defender | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 89.460 / 67.534 | 36.237 / 36.237 | 5.186 / 2.627 | 86.466 / 58.313 | 36.237 / 36.237 |
| DER.1 | 89.460 / 27.488 | 67.534 / 34.931 | 6.300 / 0.925 | 88.268 / 25.330 | 67.534 / 34.931 |
| E-Commerce | 89.460 / 31.271 | 67.534 / 0 | 6.527 / 1.118 | 86.466 / 22.119 | 43.021 / 43.021 |
| VOIP | 89.460 / 27.488 | 89.460 / 82.318 | 6.300 / 0.925 | 88.268 / 24.852 | 89.460 / 89.460 |
| HG1 | 89.460 / 67.534 | 36.237 / 36.237 | 16.779 / 8.774 | 86.466 / 58.313 | 36.237 / 36.237 |
| HG2 | 89.460 / 67.534 | 43.021 / 43.021 | 3.921 / 1.980 | 86.466 / 58.313 | 36.237 / 36.237 |
| ABSNP | 89.460 / 89.460 | 13.118 / 13.118 | 1.545 / 1.545 | 86.466 / 86.466 | 13.118 / 13.118 |
| ASFS3 | 89.460 / 89.460 | 18.498 / 18.498 | 0.615 / 0.615 | 86.466 / 86.466 | 18.498 / 18.498 |
| ASS2009 | 89.460 / 52.763 | 89.460 / 34.357 | 1.394 / 0.467 | 86.466 / 46.930 | 43.021 / 18.705 |
| AWS03 | 89.460 / 52.76 | 67.534 / 0 | 0.762 / 0.254 | 86.466 / 39.346 | 52.763 / 52.763 |
| Sum of Ranks | 44 | 26.5 | 12 | 34 | 33.5 |

2) Top-K defence.

| System | GeniGraph | Min-cut | Equally Distributed | Behavioral Defender | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 43.021 / 24.516 | 36.237 / 27.222 | 5.186 / 2.627 | 40.206 / 22.893 | 36.237 / 27.222 |
| DER.1 | 63.725 / 13.986 | 67.534 / 19.357 | 6.734 / 0.925 | 63.643 / 13.929 | 67.534 / 28.597 |
| E-Commerce } | 75.386 / 13.023 | 67.534 / 0 | 6.527 / 1.118 | 73.747 / 10.984 | 43.021 / 43.021 |
| VOIP | 79.502 / 23.795 | 89.460 / 82.318 | 6.951 / 0.925 | 72.608 / 23.981 | 89.460 / 82.318 |
| HG1 | 43.021 / 24.516 | 36.237 / 32.119 | 16.779 / 8.774 | 40.206 / 22.081 | 36.237 / 32.119 |
| HG2 | 56.387 / 18.951 | 43.021 / 10.755 | 5.347 / 1.980 | 53.835 / 16.603 | 36.237 / 30.801 |
| ABSNP | 43.021 / 29.142 | 13.118 / 13.118 | 2.685 / 1.545 | 40.986 / 26.170 | 13.118 / 10.909 |
| ASFS3 | 43.021 / 29.142 | 18.498 / 11.524 | 1.074 / 0.615 | 40.986 / 26.617 | 18.498 / 15.254 |
| ASS2009 | 73.642 / 34.875 | 89.460 / 30.944 | 1.740 / 0.467 | 69.664 / 28.959 | 43.021 / 16.846 |
| AWS03 | 72.629 / 25.364 | 67.534 / 0 | 0.888 / 0.254 | 67.169 / 21.417 | 52.763 / 42.068 |
| Sum of Ranks | 38 | 30.5 | 12 | 29 | 40.5 |

3) Weighted Top-K Defense.

| System | GeniGraph | Min-cut | Equally Distributed | Behavioral Defender | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 43.0217 / 24.516 | 36.237 / 27.222 | 5.186 / 2.627 | 40.206 / 22.893 | 36.237 / 27.222 |
| DER.1 | 64.235 / 14.385 | 67.534 / 19.357 | 6.734 / 0.925 | 63.643 / 13.929 | 67.534 / 28.597 |
| E-Commerce | 77.490 / 13.023 | 67.534 / 0 | 6.527 / 1.118 | 73.747 / 10.984 | 43.021 / 43.021 |
| VOIP | 80.433 / 24.256 | 89.460 / 82.318 | 6.951 / 0.925 | 72.608 / 23.981 | 89.460 / 82.318 |
| HG1 | 43.021 / 24.516 | 36.237 / 32.119 | 16.779 / 8.774 | 40.206 / 22.081 | 36.237 / 32.119 |
| HG2 | 51.254 / 13.802 | 43.021 / 10.755 | 5.347 / 1.980 | 53.835 / 16.603 | 36.237 / 30.801 |
| ABSNP | 40.818 / 29.815 | 13.118 / 13.118 | 2.685 / 1.545 | 40.986 / 26.170 | 13.118 / 10.909 |
| ASFS3 | 40.818 / 29.815 | 18.498 / 11.524 | 1.074 / 0.615 | 40.986 / 26.617 | 18.498 / 15.254 |
| ASS2009 | 78.510 / 41.837 | 89.460 / 30.944 | 1.740 / 0.467 | 69.664 / 28.959 | 43.021 / 16.846 |
| AWS03 | 67.289 / 29.516 | 67.534 / 0 | 888 / 0.254 | 67.169 / 21.417 | 52.763 / 42.068 |
| Sum of Ranks | 38 | 30.5 | 12 | 29 | 40.5 |

Note: much more experiments are conducted and illustrated in the paper, hence, read the paper carefully.

