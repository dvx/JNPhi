INC=-I/System/Library/Frameworks/JavaVM.framework/Headers

all:
	gcc -O3 -Wall $(INC) -g -c -fpic JNPhi.cpp
	gcc -O3 -shared -o libjnphi-jni.jnilib JNPhi.o
