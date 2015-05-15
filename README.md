## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.
Note: pass `-ea` to have asserts work.

## TODO (Ratner)
* make data generator take config file
* default config file
* allVariablesActive (MCSat passes it to SampleSat constructor, somewhere check for activeVar)
* Make SetClausesIn a member of Data, and make sure to include the softclauses.
* Test WalkSat
* Test SampleSat
* Test MCSat implementation
* Test BCMCSat
* Test VanillaMCSat
* Variance metrics / experiments & analysis


## TODO (Selsam)
* Write independent test for varToClause arrays (in progress)

### TODO (lower priority)
* Incremental maintenance of makeBreak counts / other bookeeping within MC-SAT ie between calls to inner SAT solver
* Asynchronous / parallel execution!
