import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Event } from './event.model';
import { ResponseWrapper, createRequestOption } from '../shared';

@Injectable()
export class EventService {

    private resourceUrl = SERVER_API_URL + 'api/events';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/events';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(event: Event): Observable<Event> {
        const copy = this.convert(event);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(event: Event): Observable<Event> {
        const copy = this.convert(event);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Event> {
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
     * Convert a returned JSON object to Event.
     */
    private convertItemFromServer(json: any): Event {
        const entity: Event = Object.assign(new Event(), json);
        entity.startEvent = this.dateUtils
            .convertDateTimeFromServer(json.startEvent);
        entity.endEvent = this.dateUtils
            .convertDateTimeFromServer(json.endEvent);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        return entity;
    }

    /**
     * Convert a Event to a JSON which can be sent to the server.
     */
    private convert(event: Event): Event {
        const copy: Event = Object.assign({}, event);

        copy.startEvent = this.dateUtils.toDate(event.startEvent);

        copy.endEvent = this.dateUtils.toDate(event.endEvent);

        copy.created = this.dateUtils.toDate(event.created);
        return copy;
    }
}
