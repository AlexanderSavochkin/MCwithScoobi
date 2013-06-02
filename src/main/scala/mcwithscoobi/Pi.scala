package mcwithscoobi

import scala.util.Random
import com.nicta.scoobi.Scoobi._
//import com.nicta.scoobi.io.func.FunctionInput
import com.nicta.scoobi.core.Reduction
import com.nicta.scoobi.impl.plan.source.FunctionInput


import MCUtils._

object PiMonteCarlo extends ScoobiApp {

  val numSequencies = 10
  val generateSequenceLength = 10000

  def run() = {
    val input = FunctionInput.fromFunction(numSequencies)( x=>x )
    val pi = input.mapFlatten( generateRands _ ).map( checkIfInside ).groupByKey.combine( Reduction( (x:ValueEstimator, y:ValueEstimator) => x combineEstimations y ) )
    persist( toTextFile(pi, "output", overwrite=true))
  }

  def checkIfInside( x:(Double, Double) ) = 
    if ( (x._1 * x._1) + (x._2 * x._2) < 1.0 )
      (1,ValueEstimator(1, 1.0, 0.0,0.0))
    else
      (1,ValueEstimator(1, 0.0, 0.0,0.0))

  def generateRands( seed:Int ) = {
    val rand = new Random(seed)
    (1 to generateSequenceLength) map ( _ => (rand.nextDouble(),rand.nextDouble()) )  
  }
}
