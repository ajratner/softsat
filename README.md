## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.
Note: pass `-ea` to have asserts work.

## TODO (current primary)
* *Fix issues with data generation: something wrong w/ clausesIn and/or isActive stuff?*
* *Can data generation be simplified a bit?*
* Test MCSat implementation
* Test BCMCSat
* Test VanillaMCSat
* Move e.g. MC-SAT params into config
* Make SetClausesIn a member of Data, and make sure to include the softclauses
* Write independent test for varToClause arrays (in progress)

## TODO (new components)
* **Conditional games for relative marginal comparisons**
* **JunctionTreeSAT & non-fully-connected data topology!**

### TODO (lower priority)
* Incremental maintenance of SAT Solver state *in between calls* to SAT solver (e.g. from MC-SAT outer loop)
* Asynchronous / parallel execution!
