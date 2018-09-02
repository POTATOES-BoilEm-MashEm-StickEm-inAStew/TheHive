package org.thp.thehive.services

import gremlin.scala._
import javax.inject.{Inject, Singleton}
import org.thp.scalligraph.PrivateField
import org.thp.scalligraph.models.{BaseVertexSteps, Database}
import org.thp.scalligraph.services.VertexSrv
import org.thp.thehive.models.ImpactStatus

@Singleton
class ImpactStatusSrv @Inject()(implicit db: Database) extends VertexSrv[ImpactStatus] {
  override val initialValues = Seq(
    ImpactStatus("NoImpact"),
    ImpactStatus("WithImpact"),
    ImpactStatus("NotApplicable")
  )

  override def steps(implicit graph: Graph): ImpactStatusSteps           = new ImpactStatusSteps(graph.V.hasLabel(model.label))
  override def get(id: String)(implicit graph: Graph): ImpactStatusSteps = steps.get(id)
}

class ImpactStatusSteps(raw: GremlinScala[Vertex])(implicit db: Database) extends BaseVertexSteps[ImpactStatus, ImpactStatusSteps](raw) {
  @PrivateField override def newInstance(raw: GremlinScala[Vertex]): ImpactStatusSteps = new ImpactStatusSteps(raw)
  def get(id: String): ImpactStatusSteps                                               = new ImpactStatusSteps(raw.coalesce(_.hasId(id), _.has(Key("value") of id)))
}
