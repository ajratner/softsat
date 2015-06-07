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
