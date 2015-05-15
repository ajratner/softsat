## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.
Note: pass `-ea` to have asserts work.

## TODO (Ratner)
* allVariablesActive (MCSat passes it to SampleSat constructor, somewhere check for activeVar)
* Test SampleSat
* Test WalkSat/SampleSat *specifically to make sure breakCount heuristic working! (Could be passing w/out this...)*
* Test MCSat implementation
* Test BCMCSat
* Test VanillaMCSat
* Variance metrics / experiments & analysis


## TODO (Selsam)
* Make SetClausesIn a member of Data, and make sure to include the softclauses [what's going on here?  are you working on this?]
* Write independent test for varToClause arrays (in progress)

### TODO (lower priority)
* Incremental maintenance of makeBreak counts / other bookeeping within MC-SAT ie between calls to inner SAT solver
* Asynchronous / parallel execution!
