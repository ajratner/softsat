Researching ways to handle 'hard' (deterministic, near-deterministic) constraints within the context of probabilistic inference- especially focused on templated factor graphs.  Looking into extensions of [MC-SAT][mcsat] algorithm and others.

## Usage
Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -ea -jar softsat.jar`.


## TODO (current primary)
* ***RUN TESTS!***
* **JunctionTree MC-SAT implementation**
* *Some sort of weighted-SP implementation...?*
* SampleSat: store last SAT assignment found, explore for some number of additional steps, then return with this last SAT assignment
* Basic Gibbs sampler?
* Write exact-marginal test for MC-SAT


### TODO (lower priority)
* *Clean up HardSoftDataGenerator.java (get rid of VariableId class)*
* *Move common functions into utility class (see notes in code)*
* *Clean up the multi-threaded code in ConditionalGames...*
* Asynchronous / parallel execution!
* Use Relsat (Bayardo & Pehaushek 2000) to generate larger exact solutions to compare with?

[mcsat]: http://research.microsoft.com/en-us/um/people/hoifung/papers/poon06.pdf
