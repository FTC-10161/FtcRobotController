package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Starter_Stack_Region_Color_Tester extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();                  //Matrix to hold input

    private Mat YCrCb_Image = new Mat();                          //Matrices to hold the image in different color spaces
    private Mat HSV_Image = new Mat();
    private Mat HLS_Image = new Mat();
    private Mat CIElab_Image = new Mat();
    private Mat CIEluv_Image = new Mat();



    double RGB_Red_C0 = 0;
    double RGB_Green_Difference_C1 = 0;
    double RGB_Blue_Difference_C2 = 0;
    double YCrBc_Luma_C0 = 0;
    double YCrBc_Red_Difference_C1 = 0;
    double YCrBc_Blue_Difference_C2 = 0;
    double HSV_Hue_C0 = 0;
    double HSV_Saturation_C1 = 0;
    double HSV_Value_C2 = 0;
    double HLS_Hue_C0 = 0;
    double HLS_Lightness_C1 = 0;
    double HLS_Saturation_C2 = 0;
    double CIELAB_Lightness_C0 = 0;
    double CIELAB_A_Difference_C1 = 0;
    double CIELAB_B_Difference_C2 = 0;
    double CIELUV_Lightness_C0 = 0;
    double CIELUV_U_Difference_C1 = 0;
    double CIELUV_V_Difference_C2 = 0;

    public Starter_Stack_Region_Color_Tester() {
}

@Override
public final Mat processFrame(Mat input) {
    input.copyTo(workingMatrix);

    if (workingMatrix.empty()) {
        return input;
    }

    int rowStart = 160;
    int rowEnd = 165;
    int colStart = 70;
    int colEnd = 110;

    ///////////////////////////  RGB  ///////////////////////////
    Mat RGB_StarterStackRegion = workingMatrix.submat(rowStart, rowEnd, colStart, colEnd);

    RGB_Red_C0 = Core.sumElems(RGB_StarterStackRegion).val[0];
    RGB_Green_Difference_C1 = Core.sumElems(RGB_StarterStackRegion).val[1];
    RGB_Blue_Difference_C2 = Core.sumElems(RGB_StarterStackRegion).val[2];


    ///////////////////////////  YCrBc  ///////////////////////////
    Imgproc.cvtColor(workingMatrix, YCrCb_Image, Imgproc.COLOR_RGB2YCrCb);    //Convert image/matrix from RGB to YCrBc.

    Mat YCrBc_StarterStackRegion = YCrCb_Image.submat(rowStart, rowEnd, colStart, colEnd);

    YCrBc_Luma_C0 = Core.sumElems(YCrBc_StarterStackRegion).val[0];
    YCrBc_Red_Difference_C1 = Core.sumElems(YCrBc_StarterStackRegion).val[1];
    YCrBc_Blue_Difference_C2 = Core.sumElems(YCrBc_StarterStackRegion).val[2];


    ///////////////////////////  HSV  ///////////////////////////
    Imgproc.cvtColor(workingMatrix, HSV_Image, Imgproc.COLOR_RGB2HSV);    //Convert image/matrix from RGB to HSV.

    Mat HSV_StarterStackRegion = HSV_Image.submat(rowStart, rowEnd, colStart, colEnd);

    HSV_Hue_C0 = Core.sumElems(HSV_StarterStackRegion).val[0];
    HSV_Saturation_C1 = Core.sumElems(HSV_StarterStackRegion).val[1];
    HSV_Value_C2 = Core.sumElems(HSV_StarterStackRegion).val[2];


    ///////////////////////////  HLS  ///////////////////////////
    Imgproc.cvtColor(workingMatrix, HLS_Image, Imgproc.COLOR_RGB2HLS);    //Convert image/matrix from RGB to HLS.

    Mat HLS_StarterStackRegion = HLS_Image.submat(rowStart, rowEnd, colStart, colEnd);

    HLS_Hue_C0 = Core.sumElems(HLS_StarterStackRegion).val[0];
    HLS_Lightness_C1 = Core.sumElems(HLS_StarterStackRegion).val[1];
    HLS_Saturation_C2 = Core.sumElems(HLS_StarterStackRegion).val[2];

    
    ///////////////////////////  CIELAB  ///////////////////////////
    Imgproc.cvtColor(workingMatrix, CIElab_Image, Imgproc.COLOR_RGB2Lab);    //Convert image/matrix from RGB to CIELAB.

    Mat CIELAB_StarterStackRegion = CIElab_Image.submat(rowStart, rowEnd, colStart, colEnd);

    CIELAB_Lightness_C0 = Core.sumElems(CIELAB_StarterStackRegion).val[0];
    CIELAB_A_Difference_C1 = Core.sumElems(CIELAB_StarterStackRegion).val[1];
    CIELAB_B_Difference_C2 = Core.sumElems(CIELAB_StarterStackRegion).val[2];


    ///////////////////////////  CIELUV  ///////////////////////////
    Imgproc.cvtColor(workingMatrix, CIEluv_Image, Imgproc.COLOR_RGB2Luv);    //Convert image/matrix from RGB to CIELUV.

    Mat CIELUV_StarterStackRegion = CIEluv_Image.submat(rowStart, rowEnd, colStart, colEnd);

    CIELUV_Lightness_C0 = Core.sumElems(CIELUV_StarterStackRegion).val[0];
    CIELUV_U_Difference_C1 = Core.sumElems(CIELUV_StarterStackRegion).val[1];
    CIELUV_V_Difference_C2 = Core.sumElems(CIELUV_StarterStackRegion).val[2];


    //Draw an on-screen rectangle around the region that we defined in the prior line.
    Imgproc.rectangle(workingMatrix, new Rect(colStart, rowStart, colEnd-colStart, rowEnd-rowStart), new Scalar(0, 255, 0));

    return workingMatrix;
    }
}
