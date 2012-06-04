package com.fluxtream.connectors.twitter;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.search.annotations.Indexed;

import com.fluxtream.connectors.annotations.ObjectTypeSpec;
import com.fluxtream.domain.AbstractFacet;

@Entity(name="Facet_TwitterMention")
@ObjectTypeSpec(name = "mention", value = 4, extractor=TwitterFacetExtractor.class, parallel=true, prettyname = "Mentions",
                detailsTemplate="<h3 class=\"flx-dataType\">{{userName}} Mentioned You</h3>" +
                                "<span class=\"flx-deviceIcon\" style=\"background:url('{{profileImageUrl}}'); background-size:100% 100%;\"></span>" +
                                "<div class=\"flx-deviceData\">" +
                                "    <span class=\"flx-tTime\">{{time}}</span>" +
                                "    <span class=\"flx-data\">{{description}}</span>" +
                                "</div>")
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@NamedQueries({
		@NamedQuery(name = "twitter.mention.deleteAll", query = "DELETE FROM Facet_TwitterMention facet WHERE facet.guestId=?"),
		@NamedQuery(name = "twitter.mention.between", query = "SELECT facet FROM Facet_TwitterMention facet WHERE facet.guestId=? AND facet.start>=? AND facet.end<=?"),
		@NamedQuery(name = "twitter.mention.oldest", query = "SELECT facet FROM Facet_TwitterMention facet WHERE facet.guestId=? ORDER BY facet.start ASC"),
		@NamedQuery(name = "twitter.mention.newest", query = "SELECT facet FROM Facet_TwitterMention facet WHERE facet.guestId=? ORDER BY facet.start DESC")
})
@Indexed
public class TwitterMentionFacet extends AbstractFacet {

	public long twitterId;
	
	public String text;
	public long time;

	public String profileImageUrl;

	public String userName;
	
	@Override
	protected void makeFullTextIndexable() {
		this.fullTextDescription = text;
	}
	
}