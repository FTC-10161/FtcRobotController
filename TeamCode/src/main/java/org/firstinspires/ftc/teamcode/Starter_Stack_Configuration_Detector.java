package org.firstinspires.ftc.teamcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class Starter_Stack_Configuration_Detector extends OpenCvPipeline {
    private Mat workingMatrix = new Mat();                  //Matrix to hold input


    int rowsContainingRings = 0;

    public Starter_Stack_Configuration_Detector() {
    }

    @Override
    public final Mat processFrame(Mat input) {
        input.copyTo(workingMatrix);

        if (workingMatrix.empty()) {
            return input;
        }

        int rowStart = 130;
        int rowIncrement = 5;
        int colStart = 70;
        int colEnd = 110;


        Imgproc.cvtColor(workingMatrix, workingMatrix, Imgproc.COLOR_RGB2YCrCb);    //Convert image/matrix from RGB to YCrBc.

        for (int i = 0; i < 8; i++) {
            Mat starterStackRowMatrix = workingMatrix.submat(i * rowIncrement + rowStart, (i+1) * rowIncrement + rowStart, colStart, colEnd);

            if (Core.sumElems(starterStackRowMatrix).val[2] > 25000)
                rowsContainingRings++;

            Imgproc.rectangle(workingMatrix, new Rect(colStart, i * rowIncrement + rowStart, colEnd - colStart, rowIncrement), new Scalar(0, 255, 0));
        }


        return workingMatrix;
    }
}
