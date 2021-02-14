package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Starter_Stack_Detector extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();
    public int configuration;
    public Starter_Stack_Detector() {
}

@Override
public final Mat processFrame(Mat input) {
    input.copyTo(workingMatrix);

    if (workingMatrix.empty()) {
        return input;
    }

    //Convert image/matrix from RGB to YCrBc.
    Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb);

    //Create "StarterStackRegion", which is a matrix of the region of the image in which the Starter Stack is.
    Mat StarterStackRegion = workingMatrix.submat(190, 233, 170, 210);

    //Draw an on-screen rectangle around the region that we defined in the prior line.
    Imgproc.rectangle(workingMatrix, new Rect(170, 190, 40, 30), new Scalar(0, 255, 0));

    double configuration = Core.sumElems(StarterStackRegion).val[2];

    return workingMatrix;
    }
}