package dev.maes.bookmarks.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "links")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Link implements Serializable {

	private static final long serialVersionUID = 366623853681141566L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(length = 50000)
	@NotEmpty(message = "Url may not be empty")
    private String url;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
	private Date updated_at;

}
