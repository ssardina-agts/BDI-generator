--------------------------------------------------------------------------------
***THE CODE

The code has four main packages.

1. rmit.agent.generation.templates

This package contains templates for defining agent structures. They are 'nested' 
i.e., an agent template contains plan and goal templates, a plan template 
contains a context condition template, a body method template, etc, a body 
method template contains a set of goal-posting templates, etc. This pattern 
goes all the way down to a 'code line' template for creating an arbitrary 
line of code or declaration.

Aside from missing constructs (e.g. Actions), there are some problems here at 
the moment:

	- the templates are tied in with JACK and Java (templates define class 
	  names, packages, imports, jack file extensions, etc)
	- for some reason which I can't remember, the classes are all immutable. 
	  This makes the code quite hard to follow!
	
But I don't think that fixing these will be much of a problem.

2. rmit.agent.generation.generators

This package contains the classes which actually instantiate and populate 
the templates. At the moment there are three types of generators, defined by 
three interfaces:

	- AgentGenerator
	- GPFGenerator (i.e. a goal-plan forest generator)
	- BeliefSetGenerator
	
These generators can be used independently or nested as desired.
	
In this package there some (very simple) generator implementations: 
BasicAgentGenerator, BasicGPFGenerator, RandomGPFGenerator, etc. For 
example the BasicGPFGenerator takes a few parameters such as depth, branching 
factors, a BeliefSetGenerator, etc, and produces a set of goal-
plan trees with those properties. The RandomGPFGenerator is similar but 
randomly chooses depth, etc, from a specified range.

3. rmit.agent.generation.writers

Code for writing templates to disc (i.e., as JACK code).

4. rmit.agent.generation.compilers

	- CompilerTools: code for compiling JACK source into Java source, Java 
	  source into class files, and packaging stuff into jars.
	- AgentCompiler: a 'convenience' class which handles some or all of the 
	  steps from templates -> jar file.

--------------------------------------------------------------------------------
***RUNNING THE CODE

The repo is an Eclipse project, so open it in Eclipse! (Also I haven't yet 
sorted out running this from the command line.)

Copy your jack.jar into the 'lib' directory, and add it to the build path.

Rather than using command-line parameters, the program uses properties files. 
Have a look at resources/basic.properties, and resources/random.properties. 
These files define the generator classes to be used and the parameters that 
they require.

Run TestMain. A directory called temp_[random_number] will be created, containing
jack, java, class and jar files. At the moment TestMain is hard-coded to 
read from resources/basic.properties, but of course this can be changed.




