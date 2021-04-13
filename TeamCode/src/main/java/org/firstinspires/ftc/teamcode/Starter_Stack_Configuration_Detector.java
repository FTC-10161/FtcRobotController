package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Starter_Stack_Configuration_Detector extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();                  //Matrix to hold input


    int numberOfTimesRingsDetected = 0;

    public Starter_Stack_Configuration_Detector() {
    }

    @Override
    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        int rowStart = 130;
        int rowIncrement = 1;
        int colStart = 70;
        int colEnd = 110;
        Mat starterStackRowMatrix;
        Mat nonStarterStackRowMatrix;

        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2HLS);    //Convert image/matrix from RGB to YCrBc.

        numberOfTimesRingsDetected = 0;

        for (int i = 0; i < 40; i++) {
            starterStackRowMatrix = workingMatrix.submat(i * rowIncrement + rowStart, (i+1) * rowIncrement + rowStart, colStart, colEnd);
            nonStarterStackRowMatrix = workingMatrix.submat(i * rowIncrement + rowStart, (i+1) * rowIncrement + rowStart, colStart + 50, colEnd + 50);

            if (Core.sumElems(starterStackRowMatrix).val[2] > (Core.sumElems(nonStarterStackRowMatrix).val[2] + 2000))
                numberOfTimesRingsDetected++;

            Imgproc.rectangle(workingMatrix, new Rect(colStart, i * rowIncrement + rowStart, colEnd - colStart, rowIncrement), new Scalar(0, 255, 0));
            Imgproc.rectangle(workingMatrix, new Rect(colStart + 50, i * rowIncrement + rowStart, colEnd - colStart, rowIncrement), new Scalar(0, 255, 0));
        }


        return workingMatrix;
    }
}