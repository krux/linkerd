package io.buoyant.namer.fs

import com.fasterxml.jackson.annotation.JsonIgnore
import com.twitter.finagle.Stack.Params
import com.twitter.finagle.{Stack, Path}
import io.buoyant.config.types.Directory
import io.buoyant.namer.{EnumeratingNamer, NamerConfig, NamerInitializer}

class FsInitializer extends NamerInitializer {
  val configClass = classOf[FsConfig]
  override def configId = "io.l5d.fs"
}

object FsInitializer extends FsInitializer

case class FsConfig(rootDir: Directory) extends NamerConfig {
  @JsonIgnore
  override def defaultPrefix: Path = Path.read("/io.l5d.fs")

  /**
   * Construct a namer.
   */
  @JsonIgnore
  def newNamer(params: Stack.Params) = new WatchingNamer(rootDir.path, prefix)

  @JsonIgnore
  override def newEnumeratingNamer(params: Params) =
    new WatchingNamer(rootDir.path, prefix)
}
