## Usage

Get dependencies with `sh dependencies.sh`.

Build with `ant`.

Then run with `java -jar softsat.jar`.
Note: pass `-ea` to have asserts work.

## TODO (current primary)
* Switch SAT solver/samplers to take in list of active clauses, vars (no .isActive, just check membership)
* Test basic MCSat implementation- how to do this?
* Finish general "Decomposed" MCSat w/ specifiable variable ordering
* **JunctionTree MC-SAT implementation**
* **Conditional games for relative marginal evaluation**
* Write more basic tests!

### TODO (lower priority)
* General SAT solver interface class
* Config clean up / decomposition: move e.g. MC-SAT params into separate configs?
* Incremental maintenance of SAT Solver state *in between calls* to SAT solver (e.g. from MC-SAT outer loop)
* Asynchronous / parallel execution!
