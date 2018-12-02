#include <jni.h>
#include <string>
#include <opencv2/core/core.hpp>
#include <opencv2/opencv.hpp>
#include <opencv2/core/mat.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc.hpp>
#include <memory.h>
#include <iostream>
#include <stdio.h>
#include <opencv2/imgproc/imgproc_c.h>
#include <assert.h>
#include <android/asset_manager.h>
#include <android/asset_manager_jni.h>

using namespace cv;

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_leey_findremo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

    typedef struct {
        unsigned int width;
        unsigned int height;
        unsigned int redWidth;
        unsigned int redHeight;
        unsigned int greenWidth;
        unsigned int greenHeight;
        unsigned int blueWidth;
        unsigned int blueHeight;
        unsigned char* red;
        unsigned char* green;
        unsigned char* blue;
    }NativeBitmap;

    void deleteBitmap(NativeBitmap* bitmap);
    int initBitmapMemory(NativeBitmap* bitmap, int width, int height);



    typedef struct stMap {
        unsigned char r;
        unsigned char g;
        unsigned char b;
        unsigned char gray;
    } IMGMAP;

    IMGMAP **Maps;
    int returnVal;

    void setArray(IplImage* img);
    void array2Img(IplImage* img);
    void img2Array(IplImage* img);
    void grayscaling(IplImage* img);
    void binarization(IplImage* img, int threshold);
    int computeThreshold(IplImage* img);
    IplImage* ContourExtraction(IplImage* img);
    void templatematching(IplImage* img, IplImage* img2);
    void printLabels(Mat output);


    void setArray(IplImage* img)
    {
        Maps = new IMGMAP*[img->height];
        for (int i = 0; i < img->height; i++) {
            Maps[i] = new IMGMAP[img->width];
        }
    }
    void array2Img(IplImage* img)
    {
        for (int i = 0; i < img->height; i++) {
            for (int j = 0; j < img->width; j++) {
                img->imageData[i*img->widthStep + j * img->nChannels + 2] = Maps[i][j].r;
                img->imageData[i*img->widthStep + j * img->nChannels + 1] = Maps[i][j].g;
                img->imageData[i*img->widthStep + j * img->nChannels + 0] = Maps[i][j].b;
            }
        }
    }
    void img2Array(IplImage* img)
    {
        for (int i = 0; i < img->height; i++) {
            for (int j = 0; j < img->width; j++) {
                Maps[i][j].r = img->imageData[i*img->widthStep + j * img->nChannels + 2];
                Maps[i][j].g = img->imageData[i*img->widthStep + j * img->nChannels + 1];
                Maps[i][j].b = img->imageData[i*img->widthStep + j * img->nChannels + 0];
                Maps[i][j].gray = (Maps[i][j].r + Maps[i][j].g + Maps[i][j].b) / 3;
            }
        }
    }


    void grayscaling(IplImage* img)
    {
        for (int i = 0; i < img->height; i++) {
            for (int j = 0; j < img->width; j++) {
                Maps[i][j].r = Maps[i][j].gray;
                Maps[i][j].g = Maps[i][j].gray;
                Maps[i][j].b = Maps[i][j].gray;
            }
        }
    }



    void binarization(IplImage* img, int threshold)
    {
        int thres = 230 - threshold;
        printf("Threshold : %d", thres);
        for (int i = 0; i < img->height; i++) {
            for (int j = 0; j < img->width; j++) {
                if (Maps[i][j].gray > thres) {
                    Maps[i][j].gray = 255;
                    Maps[i][j].r = 255;
                    Maps[i][j].g = 255;
                    Maps[i][j].b = 255;
                }
                else {
                    Maps[i][j].gray = 0;
                    Maps[i][j].r = 0;
                    Maps[i][j].g = 0;
                    Maps[i][j].b = 0;
                }
            }
        }
    }

    int computeThreshold(IplImage * img)
    {
        double threshold = 0;

        for (int i = 0; i < img->height; i++) {
            for (int j = 0; j < img->width; j++) {
                threshold += (int)Maps[i][j].r;
                //printf("Gray : %d\n", (int)Maps[i][j].r);
            }
        }
        threshold = threshold / ((img->height)*(img->width));

        return (int)threshold;
    }



    IplImage* ContourExtraction(IplImage* img)
    {
        int di[8] = { -1, -1, -1, 0, 0, 1, 1, 1 },
                dj[8] = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int mask[3][3] = { { -1, -1, -1 },{ -1, 8, -1 },{ -1, -1, -1 } };
        int p;
        for (int i = 1; i < img->height - 1; i++) {
            for (int j = 1; j < img->width - 1; j++) {
                p = Maps[i][j].gray;
                p *= mask[1][1];

                for (int k = 0; k < 8; k++)
                    p = p + (Maps[i + di[k]][j + dj[k]].gray * mask[1 + di[k]][1 + dj[k]]);

                if (p > 255) p = 255;
                else if (p < 0) p = 0;

                img->imageData[i*img->widthStep + j * img->nChannels + 2] = p;
                img->imageData[i*img->widthStep + j * img->nChannels + 1] = p;
                img->imageData[i*img->widthStep + j * img->nChannels + 0] = p;
            }
        }
        img2Array(img);
        return img;
    }


    void templatematching(IplImage* img, IplImage* img2) {
        //img : src, img2 : template
        int tmpImageHeight = img2->height;
        int tmpImageWidth = img2->width;
        int srcHeight = img->height;
        int srcWidth = img->width;
        float correct = 0;
        float sum = srcHeight * srcWidth;
        float max = 0;
        int flag = 0;
        unsigned char srcred, tmpred;

        for (int y = 0; y < srcHeight; y++) {
            for (int x = 0; x < srcWidth; x++) {
                srcred = Maps[y][x].r;
                tmpred = img2->imageData[y*img2->widthStep + x * img2->nChannels + 2];
                //printf("srcred : %d\n", (int)x);
                //printf("tmpRed : %d\n", (int)y);
                //printf("correct : %f\n", correct);
                if (srcred == tmpred) correct++;

            }
        }

        if ((correct / sum) > 0.85) {
            printf("Template Found %f\n", (correct / sum));
            returnVal = 0;
        }
        else{
            returnVal = -1;
        }

    }

    void printLabels(Mat output) {
        Mat img_color, img_labels, img_binary, img_gray, stats, centroids;
        Mat tmp = output;

        cvtColor(tmp, img_gray, COLOR_BGR2GRAY);
        threshold(img_gray, img_binary, 127, 255, THRESH_BINARY);
        cvtColor(img_gray, img_color, COLOR_GRAY2BGR);

        int numOfLabels = connectedComponentsWithStats(img_binary, img_labels, stats, centroids, 8, CV_32S);

        for (int y = 0; y < img_labels.rows; ++y) {
            int *label = img_labels.ptr<int>(y);
            Vec3b* pixel = img_color.ptr<Vec3b>(y);

            for (int x = 0; x < img_labels.cols; ++x) {
                if (label[x] == 3) {
                    pixel[x][2] = 255;
                    pixel[x][1] = 0;
                    pixel[x][0] = 0;
                }
            }
        }

        for (int j = 1; j < numOfLabels; j++) {
            int area = stats.at<int>(j, CC_STAT_AREA);
            int left = stats.at<int>(j, CC_STAT_LEFT);
            int top = stats.at<int>(j, CC_STAT_TOP);
            int width = stats.at<int>(j, CC_STAT_WIDTH);
            int height = stats.at<int>(j, CC_STAT_HEIGHT);

            rectangle(img_color, Point(left, top), Point(left + width, top + height), Scalar(0, 0, 255), 1);
            //putText(img_color, std::to_string(j), Point(left + 20, top + 20), FONT_HERSHEY_SIMPLEX, 1, Scalar(255, 0, 0), 2);




        }

    }

extern "C"
JNIEXPORT jint JNICALL
Java_com_cnlab_caucse_useopencvwithcmake_cameraActivity_whatisthis(JNIEnv *env, jobject instance,
                                                                   jbyteArray firstimg_,
                                                                   jbyteArray secondimg_,jint firstWidth,
                                                                   jint secondWidth, jint firstHeight,
                                                                   jint secondHeight,jobject assetManager) {
    jbyte *firstimg = env->GetByteArrayElements(firstimg_, NULL);
    jbyte *secondimg = env->GetByteArrayElements(secondimg_, NULL);

    int threshold;
    Mat img2(firstHeight, firstWidth, CV_8UC4, (unsigned char *) firstimg);
    //Build the Mat structure for output data

    Mat img(secondHeight, secondWidth, CV_8UC4, (unsigned char *) secondimg);

//    img2 = imread("assets/2.jpg");

    if (!img.data || !img2.data) {
        printf("Cannot open the image file ");
        return -1;
    }

    IplImage transpicture = img;
    IplImage *trans = cvCreateImage(cvGetSize(&transpicture), transpicture.depth, 1);

    // 에셋에서 어떤 이미지를 사용할것인지ㅣ! 그러면 사실 height랑 width만 알면되지그치?



    IplImage *result;

    IplImage templates = img2;
    IplImage *templateImg = cvCreateImage(cvGetSize(&templates), templates.depth, 1);


    setArray(trans);
    img2Array(trans);
    grayscaling(trans);
    threshold = computeThreshold(trans);
    binarization(trans, threshold);
    //result = ContourExtraction(trans);
    result = trans;
    binarization(result, 127);
    array2Img(result);
    templatematching(trans, templateImg);

    env->ReleaseByteArrayElements(firstimg_, firstimg, 0);
    env->ReleaseByteArrayElements(secondimg_, secondimg, 0);

    return returnVal;
}