#include <cassert>
#include "com_polyfx_jnphi_JNPhi.h"

jobject newInteger(JNIEnv* env, jint value) {
	jclass cls = env->FindClass("java/lang/Integer");
	jmethodID methodID = env->GetMethodID(cls, "<init>", "(I)V");
	return env->NewObject(cls, methodID, value);
}

JNIEXPORT void JNICALL Java_com_polyfx_jnphi_JNPhi_watch(JNIEnv *env, jobject thisObj) {
	
	jclass jnphiClass = env->GetObjectClass(thisObj);
	assert(jnphiClass != 0 && "jnphiClass is null");
	jmethodID waitMethodId = env->GetMethodID(jnphiClass, "wait", "()V");
	assert(waitMethodId != 0 && "waitMethodId is null");
	jmethodID notifyAllMethodId = env->GetMethodID(jnphiClass, "notifyAll", "()V");
	assert(notifyAllMethodId != 0 && "notifyAllMethodId is null");
	jfieldID executableAccessorFieldId = env->GetFieldID(jnphiClass, "accessor", "Lcom/polyfx/jnphi/ExecutableAccessor;");
	assert(executableAccessorFieldId != 0 && "executableAccessorFieldId is null");
	jobject executableAccessorObj = env->GetObjectField(thisObj, executableAccessorFieldId);
	assert(executableAccessorObj != 0 && "executableAccessorObj is null");
	jclass executableAccessorClass = env->GetObjectClass(executableAccessorObj);
	assert(executableAccessorClass != 0 && "executableAccessorClass is null");
	jmethodID consumedMethodId = env->GetMethodID(executableAccessorClass, "consumed", "()Z");
	assert(consumedMethodId != 0 && "consumedMethodId is null");
	jmethodID clearBlockMethodId = env->GetMethodID(executableAccessorClass, "clearBlock", "()V");
	assert(clearBlockMethodId != 0 && "clearBlockMethodId is null");
	jfieldID retObjFieldId = env->GetFieldID(jnphiClass, "retObj", "Ljava/lang/Object;");
	assert(retObjFieldId != 0 && "retObjFieldId is null");
	int i = 0;

	while (true) {
		// >> enter synchronized block >>
		env->MonitorEnter(thisObj);

		// we wait until there's something to consume
		while (env->CallBooleanMethod(executableAccessorObj, consumedMethodId)) {
			env->CallVoidMethod(thisObj, waitMethodId);
		}

		// do work
		env->SetObjectField(thisObj, retObjFieldId, newInteger(env, 0));

		// clean up
		env->CallVoidMethod(executableAccessorObj, clearBlockMethodId);
		env->CallVoidMethod(thisObj, notifyAllMethodId);

		// << exit synchronized block <<
		env->MonitorExit(thisObj);
	}
}

JNIEXPORT jobject JNICALL Java_com_polyfx_jnphi_JNPhi_zero(JNIEnv *env, jobject thisObj) {
	return newInteger(env, 0);
}