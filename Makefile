# this creates a jar file executable of a test simulation of the 3D Engine
smith_engine_test.jar: boot_files control_files projection_files gui_files
	jar -cvfm smith_engine_test.jar MANIFEST.mf -C classes/ .

boot_files:
	javac -cp classes/ -d classes/ src/com/potatocode/engine/boot/*.java

control_files:
	javac -cp classes/ -d classes/ src/com/potatocode/engine/controls/*.java

projection_files:
	javac -cp classes/ -d classes/ src/com/potatocode/engine/projection/*.java

gui_files:
	javac -cp classes/ -d classes/ src/com/potatocode/engine/gui/*.java

