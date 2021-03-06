package cards

sealed trait CardType {
  val cssClassName: String

  def showVideoPlayer = this match {
    case Half | ThreeQuarters | ThreeQuartersRight | Third | FullMedia50 | FullMedia75 | FullMedia100 => true
    case _ => false
  }

  def showCutOut = this match {
    case ListItem => false
    case _ => true
  }

  /** To actually find out if media is shown you would also need to check whether there is an image (or video) and
    * whether image hide setting is on.
    *
    * But some card sizes can never show media, regardless of those options, and this is what this represents.
    */
  def canShowMedia = this match {
    case ListItem => false
    case _ => true
  }

  def showStandfirst = this match {
    case Fluid | FullMedia100 | FullMedia75 | FullMedia50 | Half | ThreeQuarters | ThreeQuartersRight | Standard => true
    case _ => false
  }
}

/** This is called ListItem because List is already taken */
case object ListItem extends CardType {
  override val cssClassName = "list"
}

case object MediaList extends CardType {
  override val cssClassName = "list-media"
}

case object Standard extends CardType {
  override val cssClassName = "standard"
}

case object Half extends CardType {
  override val cssClassName = "half"
}

case object ThreeQuarters extends CardType {
  override val cssClassName: String = "three-quarters"
}

case object ThreeQuartersRight extends CardType {
  override val cssClassName: String = "three-quarters-right"
}

case object FullMedia50 extends CardType {
  override val cssClassName: String = "full-media-50"
}

case object FullMedia75 extends CardType {
  override val cssClassName: String = "full-media-75"
}

case object FullMedia100 extends CardType {
  override val cssClassName: String = "full-media-100"
}

case object Fluid extends CardType {
  override val cssClassName: String = "fluid"
}

case object Third extends CardType {
  override val cssClassName: String = "third"
}
