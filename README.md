## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.

## TODO (Ratner)
1. Global config class / object
2. SampleSat (+ WalkSat) algorithm
3. MC-SAT implementation
4. Block Gibbs + MC-SAT implementation (
5. Variance metrics / experiments & analysis

## TODO (Selsam)
1. Create VariableId type (instead of cluster/varId separate), with getters for both.
2. Refactor BGMCSat to extend SampleCollector class, which will maintain a HashMap from VariableId to Integer and count samples for both BGMCSat and VanillaMCSat.
3. Refactor BGMCSatDataGenerator to return the clusters and soft clauses separately.
4. Have BGMCSat pre-process the data to create the separate clusters it wants.
5. Write VanillaMCSat to extend SampleCollector and pre-process the data by concatenating all of the clauses.

### TODO (lower priority)
1. Incremental maintenance of makeBreak counts / other bookeeping within MC-SAT ie between calls to inner SAT solver
2. Asynchronous / parallel execution!
