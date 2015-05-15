## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.
Note: pass `-ea` to have asserts work.

## TODO (Ratner)
* Test SampleSat / fix failing WalkSat tests *(something w/ adding new isActive tests?? was passing prior to)*
* Test MCSat implementation
* Test BCMCSat
* Test VanillaMCSat
* Variance metrics / experiments & analysis


## TODO (Selsam)
* Move e.g. MC-SAT params into config
* Make SetClausesIn a member of Data, and make sure to include the softclauses [what's going on here?  are you working on this?]
* Write independent test for varToClause arrays (in progress)

### TODO (lower priority)
* Incremental maintenance of SAT Solver state *in between calls* to SAT solver (e.g. from MC-SAT outer loop)
* Asynchronous / parallel execution!
