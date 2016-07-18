package lesson1.homework

/**
  * Created by kkishore on 6/28/16.
  */
final case class LastString(value: String){

  override def toString = value
}
final case object GetLastValue