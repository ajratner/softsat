Researching ways to handle 'hard' (deterministic, near-deterministic) constraints within the context of probabilistic inference- especially focused on templated factor graphs.  Looking into extensions of [MC-SAT][mcsat] algorithm and others.

## Usage
Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -ea -jar softsat.jar`.


## TODO (current primary)
* **Conditional games for relative marginal evaluation**
* **Framework for running experiments / plotting results**
* **JunctionTree MC-SAT implementation**
* Basic Gibbs sampler?
* Write exact-marginal test for MC-SAT


### TODO (lower priority)
* *Clean up HardSoftDataGenerator.java (config, get rid of VariableId class?)*
* Asynchronous / parallel execution!
* Use Relsat (Bayardo & Pehaushek 2000) to generate larger exact solutions to compare with?

[mcsat]: http://research.microsoft.com/en-us/um/people/hoifung/papers/poon06.pdf
