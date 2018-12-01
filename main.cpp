#include <opencv2/core/core.hpp>
#include <opencv2/opencv.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <memory.h>
#include <stdio.h>
using namespace cv;

typedef struct stMap {
	unsigned char r;
	unsigned char g;
	unsigned char b;
	unsigned char gray;
} IMGMAP;


IMGMAP **Maps;

void setArray(IplImage* img);
void array2Img(IplImage* img);
void img2Array(IplImage* img);
void grayscaling(IplImage* img);
void binarization(IplImage* img, int threshold);
int computeThreshold(IplImage* img);
IplImage* ContourExtraction(IplImage* img);
void templatematching(IplImage* img, IplImage* img2);


int main()
{
	Mat img, img2;
	Mat output;
	int threshold;
	img = imread("c:\\2.jpg", IMREAD_COLOR);
	img2 = imread("c:\\2.png", IMREAD_GRAYSCALE);


	if (!img.data || !img2.data) {
		printf("Cannot open the image file ");
		return -1;
	}
	IplImage *trans;
	trans = &IplImage(img);

	IplImage *result;
	IplImage *templateImg = &IplImage(img2);



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
	output = cvarrToMat(result);



	namedWindow("Display Window", WINDOW_AUTOSIZE);
	imshow("Display Window", output);
	waitKey(0);
	return 0;
}

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
			printf("srcred : %d\n", (int)x);
			printf("tmpRed : %d\n", (int)y);
			printf("correct : %f\n", correct);
			if (srcred == tmpred) correct++;

		}
	}

	if ((correct / sum) > 0.85) {
		printf("Template Found %f\n", (correct / sum));
	}
			
}
