package io.eventmanager.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * The Invitation entity.
 */
@ApiModel(description = "The Invitation entity.")
@Entity
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "invitation")
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "corporate", length = 100, nullable = false)
    private String corporate;

    @NotNull
    @Size(min = 3, max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "phone", length = 50, nullable = false)
    private String phone;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "mobile_phone", length = 50, nullable = false)
    private String mobilePhone;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "message", length = 255, nullable = false)
    private String message;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "voice_message", length = 255, nullable = false)
    private String voiceMessage;

    @Column(name = "send_to_email")
    private Boolean sendToEmail;

    @Column(name = "send_to_call_phone")
    private Boolean sendToCallPhone;

    @Column(name = "send_to_voice_mobile_phone")
    private Boolean sendToVoiceMobilePhone;

    @Column(name = "send_to_sms_mobile_phone")
    private Boolean sendToSMSMobilePhone;

    @Column(name = "period_to_send")
    private ZonedDateTime periodToSend;

    @Column(name = "status_invitation")
    private Boolean statusInvitation;

    @Column(name = "created")
    private ZonedDateTime created;

    @ManyToOne
    private Event event;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Invitation userId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCorporate() {
        return corporate;
    }

    public Invitation corporate(String corporate) {
        this.corporate = corporate;
        return this;
    }

    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getFullName() {
        return fullName;
    }

    public Invitation fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public Invitation email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Invitation phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Invitation mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getMessage() {
        return message;
    }

    public Invitation message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVoiceMessage() {
        return voiceMessage;
    }

    public Invitation voiceMessage(String voiceMessage) {
        this.voiceMessage = voiceMessage;
        return this;
    }

    public void setVoiceMessage(String voiceMessage) {
        this.voiceMessage = voiceMessage;
    }

    public Boolean isSendToEmail() {
        return sendToEmail;
    }

    public Invitation sendToEmail(Boolean sendToEmail) {
        this.sendToEmail = sendToEmail;
        return this;
    }

    public void setSendToEmail(Boolean sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public Boolean isSendToCallPhone() {
        return sendToCallPhone;
    }

    public Invitation sendToCallPhone(Boolean sendToCallPhone) {
        this.sendToCallPhone = sendToCallPhone;
        return this;
    }

    public void setSendToCallPhone(Boolean sendToCallPhone) {
        this.sendToCallPhone = sendToCallPhone;
    }

    public Boolean isSendToVoiceMobilePhone() {
        return sendToVoiceMobilePhone;
    }

    public Invitation sendToVoiceMobilePhone(Boolean sendToVoiceMobilePhone) {
        this.sendToVoiceMobilePhone = sendToVoiceMobilePhone;
        return this;
    }

    public void setSendToVoiceMobilePhone(Boolean sendToVoiceMobilePhone) {
        this.sendToVoiceMobilePhone = sendToVoiceMobilePhone;
    }

    public Boolean isSendToSMSMobilePhone() {
        return sendToSMSMobilePhone;
    }

    public Invitation sendToSMSMobilePhone(Boolean sendToSMSMobilePhone) {
        this.sendToSMSMobilePhone = sendToSMSMobilePhone;
        return this;
    }

    public void setSendToSMSMobilePhone(Boolean sendToSMSMobilePhone) {
        this.sendToSMSMobilePhone = sendToSMSMobilePhone;
    }

    public ZonedDateTime getPeriodToSend() {
        return periodToSend;
    }

    public Invitation periodToSend(ZonedDateTime periodToSend) {
        this.periodToSend = periodToSend;
        return this;
    }

    public void setPeriodToSend(ZonedDateTime periodToSend) {
        this.periodToSend = periodToSend;
    }

    public Boolean isStatusInvitation() {
        return statusInvitation;
    }

    public Invitation statusInvitation(Boolean statusInvitation) {
        this.statusInvitation = statusInvitation;
        return this;
    }

    public void setStatusInvitation(Boolean statusInvitation) {
        this.statusInvitation = statusInvitation;
    }

    public ZonedDateTime getCreated() {
        return created;
    }

    public Invitation created(ZonedDateTime created) {
        this.created = created;
        return this;
    }

    public void setCreated(ZonedDateTime created) {
        this.created = created;
    }

    public Event getEvent() {
        return event;
    }

    public Invitation event(Event event) {
        this.event = event;
        return this;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Invitation invitation = (Invitation) o;
        if (invitation.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), invitation.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Invitation{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", corporate='" + getCorporate() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone='" + getPhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", message='" + getMessage() + "'" +
            ", voiceMessage='" + getVoiceMessage() + "'" +
            ", sendToEmail='" + isSendToEmail() + "'" +
            ", sendToCallPhone='" + isSendToCallPhone() + "'" +
            ", sendToVoiceMobilePhone='" + isSendToVoiceMobilePhone() + "'" +
            ", sendToSMSMobilePhone='" + isSendToSMSMobilePhone() + "'" +
            ", periodToSend='" + getPeriodToSend() + "'" +
            ", statusInvitation='" + isStatusInvitation() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
