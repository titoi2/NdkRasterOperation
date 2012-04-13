#include <string.h>
#include <jni.h>

jint Java_jp_kobegtug_codesprint_ndkrasteroperation_NdkRasterOperationActivity_raster( 
JNIEnv* env, jobject thiz,
jintArray bmpsrc,jintArray bmpdst, jint width, jint height, jint ope )
{
	int h,w;
 jint *src = (*env)->GetIntArrayElements(env,bmpsrc,0);
 jint *dst = (*env)->GetIntArrayElements(env,bmpdst,0);
 
	for ( h=0; h<height;h++ ) {
		int ofst = h * width;
		for ( w=0;w<width;w++ ) {
			switch (ope) {
			case 0: // copy
				src[ofst+w] = dst[ofst+w];
				break;
			case 1: // and
				src[ofst+w] &= dst[ofst+w];
				break;
			case 2: // or
				src[ofst+w] |= dst[ofst+w];
				break;
			case 3: // xor
				src[ofst+w] ^= dst[ofst+w];
				break;
			}
		}
	}
 (*env)->ReleaseIntArrayElements(env, bmpsrc, src, 0);	
 (*env)->ReleaseIntArrayElements(env, bmpdst, dst, 0);	
}
