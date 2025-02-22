# GeniGraph_Project

# Paper Link: [https://scholar.google.com/citations?view_op=view_citation&hl=ar&user=wJkf_QgAAAAJ&citation_for_view=wJkf_QgAAAAJ:IjCSPb-OGe4C](https://www.sciencedirect.com/science/article/abs/pii/S016740482400230X)

# Paper Tiltle: "GeniGraph: Genetic-based Novel Security Resource Allocation Methods for Interdependent Systems Modeled by Attack Graphs"

# abstract

We design a resource allocation framework for securing interdependent systems managed by multiple defenders. Our framework models these multi-defender interdependent systems with the notion of attack graphs. We propose three defense scenarios that are derived from the top attack paths that defenders predict, based on their system knowledge, which attackers may consider to launch their attacks. 

Furthermore, we propose a defense method with low sensitivity to the number of concurrent attacks, based on a graph-theoretical notion known as the Markov random field. We elucidate the advantages gained from our decision-making framework through its application to ten attack graphs (that includes multiple real-world interdependent systems). In particular, we quantify the level of security improvement under our defense methods compared to three well-known resource allocation algorithms. 

We demonstrate that our proposed framework leads to better resource allocations compared to these algorithms in most scenarios. We also conduct various comparisons under different parameter configurations (e.g., number of attacks, and defenders' security budgets) to demonstrate the superiority of our approach’s outcomes. We show that our framework enhances security decision-making under various circumstances.

# Framework

In our work, we start with cyber attack graph, which serves as the input for our genetic algorithm. This algorithm is designed to produce top-K potential attack paths through a series of steps including path encoding, reproduction, crossover, and mutation. Concurrently, we select a suitable fitness function and calculate the fitness and cost relative difference. The outputs from both these processes lead to the top-K paths. These paths are then utilized in various resource allocation defense scenarios, including the best attack path, attack paths equally-based, and weight-based, and Markov Random Field. Based on the evaluation of these scenarios, we make our investment decisions. Essentially, we’re optimizing investment decisions using a genetic algorithm and an attack graph, where the attack graph represents a complex system or network that the algorithm navigates to find optimal paths for investment from the prospective of defender anticipating the attacker role. The defense scenarios represent different strategies for resource allocation in response to potential risks or ‘attacks’ on the investment. Our final decision is made based on the evaluation of these scenarios.

![Screenshot (485)](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/29f2088a-b272-4a4f-9eac-9149cff14e0d)

# Fitness Function

1) $F_1(P)= \max_{P \in P_m} \big(\exp\big(-\sum_{(v_i,v_j)\in P} {x_{i,j}}\big)\big).$
2) $F_2(P) = \max_{P \in P_m} \big(\exp\big(-\sum_{(v_i,v_j)\in P} {x_{i,j}}\big) + Wf\sum_{v_m\in P} L_m\big).$
   
   $P$ is the given attack path.

   $P_m$ is a set of attack paths.

   $v_i,v_j$ are the nodes in $P$.

   $L_m$ is the loss corresponding to node $v_m$

   $Wf$ is the weight factor lies in [0,1]
   
First function cares only about the initial investments of the path $P$. On the other hand, second function accounts for the total asset loss that the system will loos if the attack is occured successfully.

# Cyber Attack Graph Example with Assets and Entry Nodes With Three Defenders to Allocate Resourcess

![Picture1](https://github.com/Mohammed-Ryiad-Eiadeh/GeniGraph_Implementation_Project/assets/93108547/870f2bf3-ed09-47be-bcaf-c122c8ea6cbf)

As the number of nodes or edges increases, we notice that the actual run-time remains relatively quick. It doesn’t display a pattern of exponential growth. The lines representing the two fitness functions, rational-𝐹1 and rational-𝐹2, in the figures do not exhibit the sharp curve usually associated with exponential growth. In fact, rational-𝐹1 shows superior performance with a slightly curved line, while rational-𝐹2 is nearly linear. This suggests that the run-time of GeniGraph grows at a rate that is not exponential.


# Our Contribution

1) We propose a resource allocation method for interdependent systems, demonstrating GeniGraph’s impact on system security and quantifying the improvement it brings.
2) We offer two versions of GeniGraph to enhance resource allocation decisions under three defense scenarios against various attack models.
3) We use a genetic algorithm to generate potential paths from the attacker’s entry node to the target, proposing two fitness functions. The first considers edge weights, while the second also accounts for estimated financial loss.
4) We evaluate our defense methods on ten attack graphs and compare the performance of three popular resource allocation methods.
5) We implement our framework in Java, using object-oriented programming and reliable libraries for graph-theoretic algorithms.

# Differencess Among our Model and Existed Ones

| System | Multiple Defenders | Interdependent Subnetworks | Analytical Framework | Behavioral Biases | Various Attack Types | Multiple Rounds | Top Attack Paths | Graph Type |
| --- | --- | --- | --- | --- | --- | --- | --- | --- |
| RAID08 [1], MILCOM06 [2] | ❌ | ✔️ | ❌ | ❌ | ❌ | ❌ | ❌ | Directed |
| S&P02 [3], CCS12 [4] | ❌ | ❌ | ✔️ | ❌ | ❌ | ❌ | ❌ | Directed |
| S&P09 [5], EC18 [6], ACSAC12 [7] | ❌ | ❌ | ❌ | ✔️ | ❌ | ❌ | ❌ | Directed |
| ICC17 [8] | ❌ | ✔️ | ✔️ | ✔️ | ❌ | ❌ | ❌ | Directed |
| TCNS20 [9], TCNS18 [10] | ✔️ | ✔️ | ✔️ | ✔️ | ❌ | ❌ | ❌ | Directed |
| Behavioral Defender [11] | ✔️ | ✔️ | ✔️ | ✔️ | ✔️ | ✔️ | ❌ | Directed |
| GeniGraph | ✔️ | ✔️ | ✔️ | ❌ | ✔️ | ✔️ | ✔️ | Bidirectional |

# Datasets We Used In Our Work

For our assessment, we used ten distinct attack graphs, each symbolizing a different interdependent system and network structure. We divided these datasets into three groups. The first group contains four attack graphs from real-world interconnected systems, namely DER.1, SCADA, E-commerce, and VOIP. Signifies an attack step, and we consider every edge to be bidirectional. The second group consists of two graph typologies, referred to as HG1 and HG2, which were introduced in earlier studies. The third group includes four datasets from a renowned interactive scientific graph data repository, named aves-sparrow-social-2009 (ASC2009), aves-sparrowlyon-flock-season3 (ASFS3), aves-weaver-social-03 (AWS03), and aves-barn-swallow-non-physical (ABSNP). This repository is a network data collection produced by top-tier US niversities. 

| System | # Nodes | # Edges | # Critical Assets | $v_s$ / $v_m$ | Graph Type | run-time $F_1$ | run-time $F_2$ |
| --- | --- | --- | --- | --- | --- | --- | --- |
| SCADA [12] | 13 | 20 | 6 | 1 / 13 | Bidirectional | 4921 | 5858 |
| DER.1 [13] | 22 | 32 | 6 | 1 / 22 | Bidirectional | 8780 | 16389 |
| E-Commerce [14] | 20 | 32 | 4 | 1 / 20 | Bidirectional | 10256 | 17902 |
| VOIP [14] | 22 | 35 | 4 | 1 / 22 | Bidirectional | 11810 | 22046 |
| HG1 [15] | 7 | 10 | 2 | 1 / 7 | Bidirectional | 3347 | 4749 |
| HG2 [15] | 15 | 22 | 5 | 1 / 12 | Bidirectional | 5436 | 6560 |
| ABSNP [16] | 17 | 122 | 6 | 1 / 17 | Bidirectional | 8107 | 6811 |
| ASFS3 [16] | 27 | 163 | 9 | 1 / 27 | Bidirectional | 11998 | 27400 |
| ASS2009 [16] | 31 | 211 | 9 | 1 / 31 | Bidirectional | 17404 | 32746 |
| AWS03 [16] | 42 | 152 | 15 | 1 / 42 | Bidirectional | 12218 | 39465 |

Note: all of these datasets are stored in the project directory and is called dynamically so no need to set up their paths.

# Construct Your Own Dataset or Database

Now, if you want to apply these approaches to your own datasets (I consider any object that holds retrievable and processable data as a database, including text files), you need to mimic our data format by following these instructions:
1) First line in your data should be something like *"|V| <--# of nodes, |E| edges"* or *"|V| <--# of nodes"* or *"|V|"*. *|V|* stands for the number of nodes, and *|E|* stands for the number of edges or connections.
2) From second line till the end, your data should be like $v_{i1}$ $v_{i2}$ (must be separated by one space) which means $v_{i1}$ has an edge to $v_{i2}$ such that *"10 25"* which means that node with $id = 10$ is connected to the node with $id = 25$. Here the initial investment is 1 by default.
3) To have connections or edges with initial investments with particular weights, your data should be formatted as $v_{i1}$ $v_{i2}$ $weight$ such that *"10 25 2.5"* means node with $id = 10$ is connected to node with $id = 25$ with a weight equals to 2.5.
4) Note any other format will crash the code, and the nodes ids must be integer (i.e., 1, 2, 3, 4, ..., *|V|*), yet not the case for the weights.

Note: For getting a deeper understanding, go to one of our projects and open one of our datasets and try to mimic its' format to construct yours.

5) After constructing your own data, drag and drop it in the projects' folder you want to work with.
6) Go to *"Attack_Defence_Graph.org"* package, then to *"Graph"* class and modify it according to your needs.
7) Then, go to *"GraphData"* class, then to *"getNodeAssetsLossValues()"* method and modify it according to your needs.

# Parameter Configuration of Our Experiments

The following parameters were used: maximum iteration ($M=2000$), population size (set of potential attack paths) ($N=2000$), mutation probability ($m_p=0.2$), mutation rate ($m_r=0.2$), and weight factor ($Wf=0.001$). The available security budgets for the defenders were $S_1=1$, $S_2=0.75$, and $S_3=0.5$. We underscore that our proposed defense strategies are effective regardless of the security budget, as demonstrated in our evaluation experiments. For the behavioral defender, the behavioral level ($a$) was set to 0.5. All experiments were performed using Java language (JDK 17) on a machine equipped with an Intel(R) Core(TM) i7-8750H CPU @ 2.20GHz (12 CPUs), and 16384MB RAM.

# Comparison of GeniGraph and baseline systems on all datasets

The row "Measurements" show the relative difference $RD$ of the fitness score corresponding to fitness function $F_1$, and the relative difference of the expected cost $CR$ for all defense scenarios. The larger $RD$ and $CR$, the better the defense method with significance level equals 0.05 for the Friedman test.

1) Top-1 attack path
   
| System | GeniGraph | Min-cut [3] | Equally Distributed [2] | Behavioral Defender [9] | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 89.460 / 67.534 | 36.237 / 36.237 | 10.64 / 5.469 | 86.466 / 58.313 | 36.237 / 36.237 |
| DER.1 | 89.460 / 27.488 | 67.534 / 34.931 | 21.815 / 3.454 | 88.268 / 25.330 | 67.534 / 34.931 |
| E-Commerce | 89.460 / 31.271 | 67.534 / 0 | 19.017 / 3.454 | 86.466 / 22.119 | 43.021 / 43.021 |
| VOIP | 89.460 / 27.488 | 89.460 / 82.318 | 20.148 / 3.163 | 88.268 / 24.852 | 89.460 / 89.460 |
| HG1 | 89.460 / 67.534 | 36.237 / 36.237 | 20.148 / 10.640 | 86.466 / 58.313 | 36.237 / 36.237 |
| HG2 | 89.460 / 67.534 | 43.021 / 43.021 | 9.721 / 4.985 | 86.466 / 58.313 | 36.237 / 36.237 |
| ABSNP | 89.460 / 89.460 | 13.118 / 13.118 | 0.917 / 0.917 | 86.466 / 86.466 | 13.118 / 13.118 |
| ASFS3 | 89.460 / 89.460 | 18.498 / 18.498 | 0.687 / 0.687 | 86.466 / 86.466 | 18.498 / 18.498 |
| ASS2009 | 89.460 / 52.763 | 89.460 / 34.357 | 1.586 / 0.531 | 86.466 / 46.930 | 43.021 / 18.705 |
| AWS03 | 89.460 / 52.76 | 67.534 / 0 | 2.195 / 0.737 | 86.466 / 39.346 | 52.763 / 52.763 |
| Sum of Ranks | 44 | 26.5 | 12 | 34 | 33.5 |

2) Top-K defence

| System | GeniGraph | Min-cut [3] | Equally Distributed [2] | Behavioral Defender [9] | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 43.021 / 24.516 | 36.237 / 27.222 | 10.64 / 5.469 | 40.206 / 22.893 | 36.237 / 27.222 |
| DER.1 | 63.725 / 13.986 | 67.534 / 19.357 | 23.165 / 3.454 | 63.643 / 13.929 | 67.534 / 28.597 |
| E-Commerce } | 75.386 / 13.023 | 67.534 / 0 | 19.017 / 3.454 | 73.747 / 10.984 | 43.021 / 43.021 |
| VOIP | 79.502 / 23.795 | 89.460 / 82.318 | 22.042 / 3.163 | 72.608 / 23.981 | 89.460 / 82.318 |
| HG1 | 43.021 / 24.516 | 36.237 / 32.119 | 20.148 / 10.640 | 40.206 / 22.081 | 36.237 / 32.119 |
| HG2 | 56.387 / 18.951 | 43.021 / 10.755 | 13.097 / 4.985 | 53.835 / 16.603 | 36.237 / 30.801 |
| ABSNP | 43.021 / 29.142 | 13.118 / 13.118 | 1.599 / 0.917 | 40.986 / 26.170 | 13.118 / 10.909 |
| ASFS3 | 43.021 / 29.142 | 18.498 / 11.524 | 1.200 / 0.687 | 40.986 / 26.617 | 18.498 / 15.254 |
| ASS2009 | 73.642 / 34.875 | 89.460 / 30.944 | 1.979 / 0.531 | 69.664 / 28.959 | 43.021 / 16.846 |
| AWS03 | 72.629 / 25.364 | 67.534 / 0 | 2.556 / 0.737 | 67.169 / 21.417 | 52.763 / 42.068 |
| Sum of Ranks | 38 | 30.5 | 12 | 29 | 40.5 |

3) Weighted Top-K Defense

| System | GeniGraph | Min-cut [3] | Equally Distributed [2] | Behavioral Defender [9] | GeniGraph (MRF) |
| --- | --- | --- | --- | --- | --- |
| SCADA | 43.0217 / 24.516 | 36.237 / 27.222 | 10.64 / 5.469 | 40.206 / 22.893 | 36.237 / 27.222 |
| DER.1 | 64.235 / 14.385 | 67.534 / 19.357 | 23.165 / 3.454 | 63.643 / 13.929 | 67.534 / 28.597 |
| E-Commerce | 77.490 / 13.023 | 67.534 / 0 | 19.017 / 3.454 | 73.747 / 10.984 | 43.021 / 43.021 |
| VOIP | 80.433 / 24.256 | 89.460 / 82.318 | 22.042 / 3.163 | 72.608 / 23.981 | 89.460 / 82.318 |
| HG1 | 43.021 / 24.516 | 36.237 / 32.119 | 20.148 / 10.640 | 40.206 / 22.081 | 36.237 / 32.119 |
| HG2 | 51.254 / 13.802 | 43.021 / 10.755 | 13.097 / 4.985 | 53.835 / 16.603 | 36.237 / 30.801 |
| ABSNP | 40.818 / 29.815 | 13.118 / 13.118 | 1.599 / 0.917 | 40.986 / 26.170 | 13.118 / 10.909 |
| ASFS3 | 40.818 / 29.815 | 18.498 / 11.524 | 1.200 / 0.687 | 40.986 / 26.617 | 18.498 / 15.254 |
| ASS2009 | 78.510 / 41.837 | 89.460 / 30.944 | 1.979 / 0.531 | 69.664 / 28.959 | 43.021 / 16.846 |
| AWS03 | 67.289 / 29.516 | 67.534 / 0 | 2.556 / 0.737 | 67.169 / 21.417 | 52.763 / 42.068 |
| Sum of Ranks | 38 | 30.5 | 12 | 29 | 40.5 |

In our study, we conducted a comprehensive experiment to evaluate the performance of our proposed method under the first fitness function $F_1$. The results are presented in the above tables for all resource allocation (defense) scenarios. 

In the first defense scenario (top-1 defense), our proposed method demonstrated superior performance compared to other baseline algorithms across different datasets. It achieved the highest rank with a sum of ranks equal to 44 and a significant $p$-value of $<0.05$, leading to the rejection of the null hypothesis.

Interestingly, the baseline [9] performed better than our proposed method (MRF), which in turn outperformed [3] and [2], respectively. 

It's worth noting that under the first defense scenario (top-1 defense), finding the top-ranked path in an environment with positive edge weights can be projected as finding the shortest path between the source and destination. To validate this equivalence, we employed the Dijkstra algorithm in our experiment. The results confirmed that our proposed method produces the same top attack path as the one produced by the Dijkstra algorithm.

This experiment was pivotal in demonstrating the effectiveness of our proposed method and provided valuable insights into its performance relative to other established algorithms.

Note: much more experiments are conducted and illustrated in the paper, hence, read the paper carefully.

# Conclusion

This work introduces an efficient defense strategy for securing interdependent systems with multiple cooperating defenders. We model these systems and their potential attack vulnerabilities using attack graphs. We present three defense scenarios: top-1 defense, top-K defense, and weighted top-K defense, each distributing resources differently based on risk levels. We also propose a version of our approach using Markov Random Field (MRF) for resource distribution.

We assessed our approaches under various parameters, security budgets, and concurrent attacks. We tested them on five real-world interdependent systems and five attack graph datasets. Our experiments suggest that our approach can enhance the security level of interdependent systems and serve as a good alternative to existing resource allocation approaches. Our open-source implementation follows object-oriented programming principles and packaging paradigm, making it easy for researchers to conduct their own experiments and modify our approaches as needed.

# How To Run The Code (read carefully please)

here we have two project where first one utilizes the first fitness function and the second one utilizes the second fitness function
1) Download intellIJ IDEA latest version
2) Dounload JDK 17 or higher
3) Set up the environment variable for the bin folder of the JDK 17+
4) Open the IDEA
5) Open the project (according to the desired fitness function)
6) Make sure you are connected to the internet
7) Wait while the IDEA download all the libraries that are included as dependencies in the pom XML file
8) Go to the main package (here you will get differenr main packages where each refers to a defense approach including two main files (executable) one of them is based on the F1 and the another is based on F2).
9) Go to the desired defense approach.
10) Set up the desired hyperparameters.
11) Run the file to see the results.


# References

1) [1] G. Modelo-Howard, S. Bagchi, and G. Lebanon. 2008. Determining placement of intrusion detectors for a distributed application through bayesian network modeling. In International Workshop on Recent Advances in Intrusion Detection. Springer, 271–290.
2) [2] R. Lippmann, K. Ingols, C. Scott, K. Piwowarski, K. Kratkiewicz, M. Artz, and R. Cunningham. 2006. Validating and restoring defense in depth using attack graphs. In IEEE Military Communications Conference. IEEE, 1–10.
3) [3] O. Sheyner, J. Haines, S. Jha, R. Lippmann, and J. M. Wing. 2002. Automated generation and analysis of attack graphs. In Proceedings 2002 IEEE Symposium on Security and Privacy. IEEE, 273–284.
4) [4] G. Yan, R. Lee, A. Kent, and D. Wolpert. 2012. Towards a bayesian network game framework for evaluating DDoS attacks and defense. In Proceedings of the 2012 ACM conference on Computer and communications security (CCS). 553–566.
5) [5] A. Acquisti. 2009. Nudging privacy: The behavioral economics of personal information. IEEE security & privacy 7, 6 (2009).
6) [6] E. M. Redmiles, M. L. Mazurek, and J. P. Dickerson. 2018. Dancing pigs or externalities?: Measuring the rationality of security decisions. In Proceedings of the 2018 ACM Conference on Economics and Computation. ACM, 215–232.
7) [7] R. Anderson. 2012. Security economics: a personal perspective. In Proceedings of the 28th Annual Computer Security Applications Conference. ACM, 139–144.
8) [8] A. Sanjab, W. Saad, and T. Başar. 2017. Prospect theory for enhanced cyber-physical security of drone delivery systems: A network interdiction game. In Communications (ICC), 2017 IEEE International Conference on. IEEE, 1–6.
9) [9] Mustafa Abdallah, Parinaz Naghizadeh, Ashish R. Hota, Timothy Cason, Saurabh Bagchi, and Shreyas Sundaram. 2020. Behavioral and Game-Theoretic Security Investments in Interdependent Systems Modeled by Attack Graphs. IEEE Transactions on Control of Network Systems 7, 4 (2020), 1585–1596. https: //doi.org/10.1109/TCNS.2020.2988007 arXiv:2001.03213.
10) [10] A. R. Hota and S. Sundaram. 2018. Interdependent Security Games on Networks Under Behavioral Probability Weighting. IEEE Transactions on Control of Network Systems 5, 1 (March 2018), 262–273. https://doi.org/10.1109/TCNS.2016.2600484.
11) [11] M. Abdallah, D. Woods, P. Naghizadeh, I. Khalil, T. Cason, S. undaram, and S. Bagchi. 2021. Morshed: Guiding behavioral decision-makers towards better security investment in interdependent systems. In Proceedings of the 2021 ACM Asia Conference on Computer and Communications Security. 378–392.
12) [12] A. R. Hota, A. Clements, S. Sundaram, and S. Bagchi. 2016. Optimal and game-theoretic deployment of security investments in interdependent assets. In International Conference on Decision and Game Theory for Security. 101–113.
13) [13] S. Jauhar, B. Chen, W. G. Temple, X. Dong, Z. Kalbarczyk, W. H. Sanders, and D. M. Nicol. 2015. Model-based cybersecurity assessment with nescor smart grid failure scenarios. In Dependable Computing (PRDC), 2015 IEEE 21st Pacific Rim International Symposium on. IEEE, 319–324.
14) [14] G. Modelo-Howard, S. Bagchi, and G. Lebanon. 2008. Determining placement of intrusion detectors for a distributed application through bayesian network modeling. In International Workshop on Recent Advances in Intrusion Detection. Springer, 271–290.
15) [15] Jianping Zeng, Shuang Wu, Yanyu Chen, Rui Zeng, and Chengrong Wu. 2019. Survey of Attack Graph Analysis Methods from the Perspective of Data and Knowledge Processing. Security and Communication Networks 2019 (2019). https: //doi.org/10.1155/2019/2031063.
16) [16] Ryan A. Rossi and Nesreen K. Ahmed. 2015. The Network Data Repository with Interactive Graph Analytics and Visualization. In Proceedings of the Twenty-Ninth AAAI Conference on Artificial Intelligence. http://networkrepository.com.

# Contact With The Authors

Send email to the following Authors for any question about this work, and it is our pleasure to ansawer your question.

Mohammad Aleiadeh, maleiade@purdue.edu

Mustafa Abdallah, abdalla0@purdue.edu


