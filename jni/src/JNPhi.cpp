#include <cassert>
#include <iostream>
#include "com_polyfx_jnphi_JNPhi.h"

const int NO_RETURN = 0;

jobject newInteger(JNIEnv* env, jint value) {
	jclass cls = env->FindClass("java/lang/Integer");
	jmethodID methodID = env->GetMethodID(cls, "<init>", "(I)V");
	return env->NewObject(cls, methodID, value);
}

JNIEXPORT void JNICALL Java_com_polyfx_jnphi_JNPhi_watch(JNIEnv *env, jobject thisObj) {

	std::cout << "welcome to the DLL" << std::endl;
	
	jclass jnphiClass = env->GetObjectClass(thisObj);
	assert(jnphiClass != 0 && "jnphiClass is null");
	jmethodID waitMethodId = env->GetMethodID(jnphiClass, "wait", "()V");
	assert(waitMethodId != 0 && "waitMethodId is null");
	jmethodID notifyAllMethodId = env->GetMethodID(jnphiClass, "notifyAll", "()V");
	assert(notifyAllMethodId != 0 && "notifyAllMethodId is null");
	jmethodID consumedMethodId = env->GetMethodID(jnphiClass, "consumed", "()Z");
	assert(consumedMethodId != 0 && "consumedMethodId is null");
	jmethodID resetMethodId = env->GetMethodID(jnphiClass, "reset", "()V");
	assert(resetMethodId != 0 && "resetMethodId is null");

	// retType
	jfieldID retTypeFieldId = env->GetFieldID(jnphiClass, "retType", "I");
	assert(retTypeFieldId != 0 && "retTypeFieldId is null");

	// potential return objects
	jfieldID retBooleanFieldId = env->GetFieldID(jnphiClass, "retBoolean", "Z");
	assert(retBooleanFieldId != 0 && "retBooleanFieldId is null");
	jfieldID retByteFieldId = env->GetFieldID(jnphiClass, "retByte", "B");
	assert(retByteFieldId != 0 && "retByteFieldId is null");
	jfieldID retCharFieldId = env->GetFieldID(jnphiClass, "retChar", "C");
	assert(retCharFieldId != 0 && "retCharFieldId is null");
	jfieldID retShortFieldId = env->GetFieldID(jnphiClass, "retShort", "S");
	assert(retShortFieldId != 0 && "retShortFieldId is null");
	jfieldID retIntFieldId = env->GetFieldID(jnphiClass, "retInt", "I");
	assert(retIntFieldId != 0 && "retIntFieldId is null");
	jfieldID retLongFieldId = env->GetFieldID(jnphiClass, "retLong", "J");
	assert(retLongFieldId != 0 && "retLongFieldId is null");
	jfieldID retFloatFieldId = env->GetFieldID(jnphiClass, "retFloat", "F");
	assert(retFloatFieldId != 0 && "retFloatFieldId is null");
	jfieldID retDoubleFieldId = env->GetFieldID(jnphiClass, "retDouble", "D");
	assert(retDoubleFieldId != 0 && "retDoubleFieldId is null");
	jfieldID retObjFieldId = env->GetFieldID(jnphiClass, "retObject", "Ljava/lang/Object;");
	assert(retObjFieldId != 0 && "retObjectFieldId is null");

	int i = 0;

	while (true) {
		++i;
		// >> enter synchronized block >>
		env->MonitorEnter(thisObj);

		// we wait until there's something to consume
		while (env->CallBooleanMethod(thisObj, consumedMethodId)) {
			env->CallVoidMethod(thisObj, waitMethodId);
		}

		// do work
		env->SetObjectField(thisObj, retObjFieldId, newInteger(env, 42));

		// clean up
		env->SetIntField(thisObj, retTypeFieldId, 0);
		env->CallVoidMethod(thisObj, notifyAllMethodId);

		// << exit synchronized block <<
		env->MonitorExit(thisObj);
	}
}

JNIEXPORT jobject JNICALL Java_com_polyfx_jnphi_JNPhi_zero(JNIEnv *env, jobject thisObj) {
	return newInteger(env, 42);
}