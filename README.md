###0.3.6 [2015-09-14]
* Added debug function "drawtree" that visualizes the tree data structure.
* Console doesn't store the last command to previous commands if the last command was the same.
* Implemented KD-tree data structure, replacing the quadtree, further improving performance.

###0.3.5 [2015-09-13]
* Implemented a quadtree data structure for collision detection, greatly improving performance, especially for larger number of cells.
* Added more variables available with the get command in the console: "treebuildtime" and "cellrequests"

###0.3.4 [2015-09-13]
* Organism object is now fully omitted and removed all legacy use of it.

###0.3.3 [2015-09-13]
* New cells now properly checks if there are more cells around it that it could bind to. It achieves this by working its way around from its parent asking already bound neighbors for  their neighbors. This still needs more work however.
* Due to the improved binding algorithm some of the graphical glitches are now fixed.
* Phantom forces has been located to be caused when two cells grow into the same location where there's no room for both.
* Test organisms now start with a random color instead to make them stand out more from each other.
* Added more debugging functionality adding functions "debug drawskeleton" as well as "debug drawborder" to console.

###0.3.2 [2015-03-04]
* Implemented core structure for DNA
* First draft at cell division and growth based on DNA
* Previously used "Organism" object now redundant and omitted as all interactions are done on a cellular level and the engine does not any longer need to keep track of which cells belongs to which organism.
* Known bug: phantom forces in some generated organisms
* Known bug: minor graphical glitches in some cell configurations

###0.3.1 [2015-03-01]
* Added support to reuse previous used commands in console (up and down arrow-keys.)

###0.3.0 [2015-03-01]
* Due to many visitors using Linux I decided to port the entire project from C# .NET to Java. This happened to be a great move as performance, especially for graphics, increased.
* New console which accepts input! (great for debugging!)
* Groundwork for cell growth implemented.

###0.2.1 [2015-01-12]
* Implemented pain/pleasure (punish/reward) system that spreads through the organism.
* Implemented graphics visualizing pain/pleasure
* Note: No system invoking pleasure is implemented yet.

###0.2.0 [2015-01-08]
* Major graphics engine redesign.
* Organism outline is now drawn on a cellular level
* Fixed some major graphical glitches caused when an organism was cut in half.

###0.1.2 [2014-12-17]
* Minor bug fixes and tweaking.
* Testing new system where cells break apart from each other.

###0.1.1 [2014-12-15]
* Improved graphics engine.
* Minor bug fixes.

###0.1.0 [2014-12-13]
* First draft of physics engine.