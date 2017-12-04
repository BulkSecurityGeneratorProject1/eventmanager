import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Invitation } from './invitation.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class InvitationService {

    private resourceUrl = SERVER_API_URL + 'api/invitations';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/invitations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(invitation: Invitation): Observable<Invitation> {
        const copy = this.convert(invitation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(invitation: Invitation): Observable<Invitation> {
        const copy = this.convert(invitation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Invitation> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Invitation.
     */
    private convertItemFromServer(json: any): Invitation {
        const entity: Invitation = Object.assign(new Invitation(), json);
        entity.periodToSend = this.dateUtils
            .convertDateTimeFromServer(json.periodToSend);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Invitation to a JSON which can be sent to the server.
     */
    private convert(invitation: Invitation): Invitation {
        const copy: Invitation = Object.assign({}, invitation);

        copy.periodToSend = this.dateUtils.toDate(invitation.periodToSend);

        copy.created = this.dateUtils.toDate(invitation.created);
        return copy;
    }
}
