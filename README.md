## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.

## TO-DO
1. ~~Dataset generator~~
2. Global config class / object
3. SampleSat (+ WalkSat) algorithm
4. MC-SAT implementation
5. Block Gibbs + MC-SAT implementation
6. Variance metrics / experiments & analysis

### Lower-priority to-do
1. Incremental maintenance of makeBreak counts / other bookeeping within MC-SAT ie between calls to inner SAT solver
2. Asynchronous / parallel execution!
