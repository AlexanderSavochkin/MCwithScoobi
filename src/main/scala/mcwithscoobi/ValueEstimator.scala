package mcwithscoobi

import com.nicta.scoobi.Scoobi._

object MCUtils {

    //Kahan-like values combination algorithm
    case class ValueEstimator(numTests:Long, mean:Double, variance:Double, meanError:Double) {
        def combineEstimations( other:ValueEstimator ):ValueEstimator = {
            val combinedNumberOfTests = numTests + other.numTests
            val combinedMean = (numTests * (mean + meanError) + other.numTests * (other.mean + other.meanError)) / combinedNumberOfTests
            val combinedVariance = {
                val sqDeviationSumThis = variance * numTests * (numTests - 1)
                val sqDeviationSumOther = other.variance * other.numTests * (other.numTests - 1)
                val delta = mean - other.mean
                val sqDeviationSumTotal = sqDeviationSumThis + sqDeviationSumOther + delta * delta * numTests * other.numTests / combinedNumberOfTests
                sqDeviationSumTotal / (combinedNumberOfTests * (combinedNumberOfTests - 1))
            }
            val combinedMeanError = if (numTests > other.numTests)
                                     -((combinedMean * combinedNumberOfTests - numTests * mean) - other.numTests * other.mean) / combinedNumberOfTests
                                 else
                                     -((combinedMean * combinedNumberOfTests - other.numTests * other.mean) - numTests * mean) / combinedNumberOfTests
            ValueEstimator(combinedNumberOfTests, combinedMean, combinedVariance, combinedMeanError)
        }
    }

    implicit val valueEstimatorFmt = mkCaseWireFormat(ValueEstimator, ValueEstimator.unapply _)

}